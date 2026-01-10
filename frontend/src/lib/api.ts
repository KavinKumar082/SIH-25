import axios from 'axios';

/**
 * Central Axios instance for all API calls
 * Backend Base URL: http://localhost:8080
 * 
 * NOTE: In production, update the baseURL to match your deployed backend
 */
const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 30000, // 30 seconds timeout for AI operations
});

// Request interceptor for logging (helpful for debugging)
api.interceptors.request.use(
  (config) => {
    console.log(`[API] ${config.method?.toUpperCase()} ${config.url}`);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('[API Error]', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export default api;

// ==========================================
// API FUNCTIONS - Connected to Spring Boot Backend
// ==========================================

/**
 * Run the AI Priority Engine for case intelligence
 * POST /api/case-intelligence/run?page=0&size=5
 */
export const runCaseIntelligence = async (page = 0, size = 5) => {
  const response = await api.post(`/api/case-intelligence/run?page=${page}&size=${size}`);
  return response.data;
};

/**
 * Get AI Recommendation for a specific region
 * GET /api/recommendation/{regionId}?page=0&size=10
 */
export const getRecommendation = async (regionId: number, page = 0, size = 10) => {
  const response = await api.get(`/api/recommendation/${regionId}?page=${page}&size=${size}`);
  return response.data;
};

/**
 * Get DCA Performance data
 * GET /api/dca/{dcaId}/performance/latest?period=MONTHLY
 */
export const getDCAPerformance = async (dcaId: number, period = 'MONTHLY') => {
  const response = await api.get(`/api/dca/${dcaId}/performance/latest?period=${period}`);
  return response.data;
};

/**
 * Allocate a case to a DCA
 * POST /api/allocation
 */
export const allocateCase = async (accountId: number, dcaId: number, priorityScore: number) => {
  const response = await api.post('/api/allocation', {
    accountId,
    dcaId,
    priorityScore,
  });
  return response.data;
};

// ==========================================
// TYPE DEFINITIONS
// ==========================================

export interface DCA {
  dcaId: number;
  name: string;
  commissionRate: number;
}

export interface CaseData {
  accountId: number;
  customerName: string;
  location: string;
  delinquencyScore: number;
  agingBucket: string;
  priorityScore: number;
  outstandingAmount?: number;
  isAssigned: boolean;
  assignedDcaId?: number;
  assignedDcaName?: string;
}

export interface AIRecommendation {
  accountId: number;
  dcaId: number;
  dcaName: string;
  reason: string;
}

export interface DCAPerformance {
  dcaId: number;
  dcaName: string;
  recoveryRate: number;
  avgResolutionDays: number;
  escalations: number;
  totalCases: number;
  successRate: number;
  period: string;
}

export interface AllocatedCase {
  accountId: number;
  customerName: string;
  priorityScore: number;
  outstandingAmount?: number;
  location?: string;
  delinquencyScore?: number;
  assignedDate: string;
  slaDueDate: string;
  status: 'NEW' | 'RUNNING' | 'RESOLVED' | 'ESCALATED';
}
