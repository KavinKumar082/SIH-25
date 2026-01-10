import React, { createContext, useContext, useState, ReactNode } from 'react';

type UserRole = 'FEDEX_ADMIN' | 'DCA_USER' | null;

interface AuthContextType {
  userRole: UserRole;
  dcaId: number | null;
  login: (role: UserRole, dcaId?: number) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [userRole, setUserRole] = useState<UserRole>(null);
  const [dcaId, setDcaId] = useState<number | null>(null);

  const login = (role: UserRole, selectedDcaId?: number) => {
    setUserRole(role);
    if (role === 'DCA_USER' && selectedDcaId) {
      setDcaId(selectedDcaId);
    }
  };

  const logout = () => {
    setUserRole(null);
    setDcaId(null);
  };

  return (
    <AuthContext.Provider
      value={{
        userRole,
        dcaId,
        login,
        logout,
        isAuthenticated: userRole !== null,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
