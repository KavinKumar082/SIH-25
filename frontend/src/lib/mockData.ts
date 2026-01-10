import type { DCA, CaseData, AllocatedCase } from './api';

/**
 * ⚠️ MOCK DATA - Replace with API calls in production
 * 
 * This file contains hardcoded data for demonstration purposes.
 * Each section has comments indicating which API to use for real data.
 */

// ==========================================
// DCA LIST - Replace this with GET /api/dca in future
// ==========================================
export const dcas: DCA[] = [
  { dcaId: 1, name: "Chennai DCA", commissionRate: 12 },
  { dcaId: 2, name: "Bangalore DCA", commissionRate: 10 },
  { dcaId: 3, name: "Mumbai DCA", commissionRate: 11 },
  { dcaId: 4, name: "Delhi DCA", commissionRate: 9 },
  { dcaId: 5, name: "Hyderabad DCA", commissionRate: 10.5 },
];

// ==========================================
// REGIONS - Hardcoded mapping
// ==========================================
export const regions = [
  { regionId: 1, name: "Chennai" },
  { regionId: 2, name: "Bangalore" },
  { regionId: 3, name: "Mumbai" },
  { regionId: 4, name: "Delhi" },
  { regionId: 5, name: "Hyderabad" },
];

// ==========================================
// MOCK CASES - Replace with actual DB data from backend
// Enhanced with outstanding amounts for admin visibility
// ==========================================
export const mockCases: CaseData[] = [
  {
    accountId: 101,
    customerName: "Rajesh Kumar",
    location: "Chennai",
    delinquencyScore: 85,
    agingBucket: "60-90 Days",
    priorityScore: 87.5,
    outstandingAmount: 125000,
    isAssigned: false,
  },
  {
    accountId: 102,
    customerName: "Priya Sharma",
    location: "Chennai",
    delinquencyScore: 72,
    agingBucket: "30-60 Days",
    priorityScore: 75.2,
    outstandingAmount: 78500,
    isAssigned: false,
  },
  {
    accountId: 103,
    customerName: "Amit Patel",
    location: "Bangalore",
    delinquencyScore: 91,
    agingBucket: "90+ Days",
    priorityScore: 92.1,
    outstandingAmount: 245000,
    isAssigned: false,
  },
  {
    accountId: 104,
    customerName: "Sneha Reddy",
    location: "Bangalore",
    delinquencyScore: 68,
    agingBucket: "30-60 Days",
    priorityScore: 71.8,
    outstandingAmount: 56200,
    isAssigned: false,
  },
  {
    accountId: 105,
    customerName: "Vikram Singh",
    location: "Mumbai",
    delinquencyScore: 78,
    agingBucket: "60-90 Days",
    priorityScore: 80.3,
    outstandingAmount: 189000,
    isAssigned: false,
  },
  {
    accountId: 106,
    customerName: "Anita Desai",
    location: "Mumbai",
    delinquencyScore: 95,
    agingBucket: "90+ Days",
    priorityScore: 94.7,
    outstandingAmount: 312500,
    isAssigned: false,
  },
  {
    accountId: 107,
    customerName: "Rahul Mehta",
    location: "Delhi",
    delinquencyScore: 62,
    agingBucket: "0-30 Days",
    priorityScore: 65.4,
    outstandingAmount: 42300,
    isAssigned: false,
  },
  {
    accountId: 108,
    customerName: "Kavitha Nair",
    location: "Hyderabad",
    delinquencyScore: 88,
    agingBucket: "60-90 Days",
    priorityScore: 89.2,
    outstandingAmount: 167800,
    isAssigned: false,
  },
];

// ==========================================
// MOCK ALLOCATED CASES FOR DCA DASHBOARD
// Replace with GET /api/dca/{dcaId}/cases in future
// Enhanced with more details for DCA view
// ==========================================
export const mockAllocatedCases: AllocatedCase[] = [
  {
    accountId: 201,
    customerName: "Suresh Menon",
    priorityScore: 82.5,
    outstandingAmount: 145000,
    location: "Chennai",
    delinquencyScore: 78,
    assignedDate: "2024-01-15",
    slaDueDate: "2024-02-15",
    status: "NEW",
  },
  {
    accountId: 202,
    customerName: "Lakshmi Iyer",
    priorityScore: 91.2,
    outstandingAmount: 228000,
    location: "Chennai",
    delinquencyScore: 89,
    assignedDate: "2024-01-10",
    slaDueDate: "2024-02-10",
    status: "RUNNING",
  },
  {
    accountId: 203,
    customerName: "Arjun Kapoor",
    priorityScore: 76.8,
    outstandingAmount: 89500,
    location: "Bangalore",
    delinquencyScore: 71,
    assignedDate: "2024-01-08",
    slaDueDate: "2024-02-08",
    status: "RESOLVED",
  },
  {
    accountId: 204,
    customerName: "Meera Joshi",
    priorityScore: 88.3,
    outstandingAmount: 178200,
    location: "Mumbai",
    delinquencyScore: 85,
    assignedDate: "2024-01-12",
    slaDueDate: "2024-02-12",
    status: "RUNNING",
  },
  {
    accountId: 205,
    customerName: "Ravi Shankar",
    priorityScore: 95.1,
    outstandingAmount: 356000,
    location: "Delhi",
    delinquencyScore: 94,
    assignedDate: "2024-01-05",
    slaDueDate: "2024-02-05",
    status: "ESCALATED",
  },
];

// ==========================================
// MOCK SUMMARY STATS - Replace with aggregated API data
// ==========================================
export const mockSummaryStats = {
  recoveryRate: 73.5,
  slaBreaches: 12,
  escalations: 8,
  successScore: 85.2,
  totalCases: 156,
  pendingCases: 42,
  resolvedCases: 98,
};

// ==========================================
// MOCK PERFORMANCE DATA FOR CHARTS
// ==========================================
export const mockPerformanceHistory = [
  { month: "Aug", recoveryRate: 68, resolutionDays: 18, escalations: 5 },
  { month: "Sep", recoveryRate: 71, resolutionDays: 16, escalations: 4 },
  { month: "Oct", recoveryRate: 69, resolutionDays: 17, escalations: 6 },
  { month: "Nov", recoveryRate: 74, resolutionDays: 14, escalations: 3 },
  { month: "Dec", recoveryRate: 76, resolutionDays: 13, escalations: 4 },
  { month: "Jan", recoveryRate: 78, resolutionDays: 12, escalations: 2 },
];

// ==========================================
// MOCK DCA PERFORMANCE FOR RECOMMENDATIONS
// Replace with GET /api/dca/{dcaId}/performance/latest in future
// ==========================================
export const mockDCAPerformances: Record<number, { recoveryRate: number; avgResolutionDays: number; escalations: number }> = {
  1: { recoveryRate: 78.5, avgResolutionDays: 12, escalations: 3 },
  2: { recoveryRate: 82.3, avgResolutionDays: 10, escalations: 2 },
  3: { recoveryRate: 75.1, avgResolutionDays: 14, escalations: 5 },
  4: { recoveryRate: 71.8, avgResolutionDays: 16, escalations: 6 },
  5: { recoveryRate: 79.2, avgResolutionDays: 11, escalations: 4 },
};
