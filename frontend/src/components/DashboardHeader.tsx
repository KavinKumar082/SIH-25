import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';
import { Button } from '@/components/ui/button';
import { LogOut, LayoutDashboard, FileText, Building2 } from 'lucide-react';
import { cn } from '@/lib/utils';

interface NavItem {
  label: string;
  path: string;
  icon: React.ReactNode;
}

export function DashboardHeader() {
  const { userRole, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  // Determine if we're in FedEx or DCA section based on URL
  const isFedExSection = location.pathname.startsWith('/fedex');
  const isDCASection = location.pathname.startsWith('/dca');

  const fedexNavItems: NavItem[] = [
    { label: 'Dashboard', path: '/fedex/dashboard', icon: <LayoutDashboard className="h-4 w-4" /> },
    { label: 'Cases (Assign)', path: '/fedex/cases', icon: <FileText className="h-4 w-4" /> },
  ];

  const dcaNavItems: NavItem[] = [
    { label: 'Dashboard', path: '/dca/dashboard', icon: <LayoutDashboard className="h-4 w-4" /> },
  ];

  // Use URL to determine nav items, fallback to userRole context
  const navItems = isFedExSection ? fedexNavItems : 
                   isDCASection ? dcaNavItems :
                   userRole === 'FEDEX_ADMIN' ? fedexNavItems : dcaNavItems;

  const displayRole = isFedExSection ? 'FEDEX_ADMIN' :
                      isDCASection ? 'DCA_USER' :
                      userRole;

  const handleLogout = () => {
    logout();
    localStorage.removeItem('dcaId');
    navigate('/login');
  };

  return (
    <header className="sticky top-0 z-40 bg-dashboard-header text-dashboard-header-foreground shadow-lg">
      <div className="flex h-16 items-center justify-between px-6">
        {/* Logo & Brand */}
        <div className="flex items-center gap-3">
          <Building2 className="h-8 w-8 text-primary" />
          <div>
            <h1 className="text-lg font-bold tracking-tight">DCA Management</h1>
            <p className="text-xs text-muted-foreground/70">
              {displayRole === 'FEDEX_ADMIN' ? 'FedEx Operations Portal' : 'DCA Member Portal'}
            </p>
          </div>
        </div>

        {/* Navigation */}
        <nav className="flex items-center gap-1">
          {navItems.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={cn(
                'flex items-center gap-2 rounded-lg px-4 py-2 text-sm font-medium transition-colors',
                location.pathname === item.path || location.pathname.startsWith(item.path.replace('/dashboard', '/dca/'))
                  ? 'bg-primary text-primary-foreground'
                  : 'text-muted-foreground hover:bg-muted/10 hover:text-primary-foreground'
              )}
            >
              {item.icon}
              {item.label}
            </Link>
          ))}
        </nav>

        {/* User Actions */}
        <div className="flex items-center gap-4">
          <span className="text-sm text-muted-foreground">
            {displayRole === 'FEDEX_ADMIN' ? 'Operations Admin' : 'DCA Member'}
          </span>
          <Button
            variant="ghost"
            size="sm"
            onClick={handleLogout}
            className="text-muted-foreground hover:bg-destructive hover:text-destructive-foreground"
          >
            <LogOut className="mr-2 h-4 w-4" />
            Logout
          </Button>
        </div>
      </div>
    </header>
  );
}
