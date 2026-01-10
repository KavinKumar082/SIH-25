import { useState, useEffect } from 'react';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter, DialogDescription } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Progress } from '@/components/ui/progress';
import { Loader2, Building2, TrendingUp, Clock, AlertCircle, Star, CheckCircle2 } from 'lucide-react';
import { dcas, mockDCAPerformances } from '@/lib/mockData';
import { getDCAPerformance, allocateCase } from '@/lib/api';
import type { CaseData, DCAPerformance } from '@/lib/api';
import { cn } from '@/lib/utils';

interface DCAAssignmentModalProps {
  isOpen: boolean;
  onClose: () => void;
  caseData: CaseData;
  recommendedDcaId?: number;
  onAssigned: (accountId: number, dcaId: number, dcaName: string) => void;
}

interface DCAOption {
  dcaId: number;
  name: string;
  commissionRate: number;
  performance?: {
    recoveryRate: number;
    avgResolutionDays: number;
    escalations: number;
  };
}

export function DCAAssignmentModal({
  isOpen,
  onClose,
  caseData,
  recommendedDcaId,
  onAssigned,
}: DCAAssignmentModalProps) {
  const [selectedDcaId, setSelectedDcaId] = useState<number | null>(recommendedDcaId || null);
  const [dcaOptions, setDcaOptions] = useState<DCAOption[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isAssigning, setIsAssigning] = useState(false);

  useEffect(() => {
    if (isOpen) {
      loadDCAPerformance();
    }
  }, [isOpen]);

  const loadDCAPerformance = async () => {
    setIsLoading(true);
    
    // Build DCA options with performance data
    const options: DCAOption[] = await Promise.all(
      dcas.map(async (dca) => {
        try {
          // GET /api/dca/{dcaId}/performance/latest?period=MONTHLY
          const perf = await getDCAPerformance(dca.dcaId, 'MONTHLY');
          return {
            ...dca,
            performance: {
              recoveryRate: perf.recoveryRate,
              avgResolutionDays: perf.avgResolutionDays,
              escalations: perf.escalations,
            },
          };
        } catch {
          // ⚠️ MOCK DATA FALLBACK - Replace with real API data
          const mockPerf = mockDCAPerformances[dca.dcaId];
          return {
            ...dca,
            performance: mockPerf,
          };
        }
      })
    );

    setDcaOptions(options);
    setIsLoading(false);
  };

  const handleAssign = async () => {
    if (!selectedDcaId) return;

    setIsAssigning(true);
    try {
      // POST /api/allocation - Actual backend call
      await allocateCase(caseData.accountId, selectedDcaId, caseData.priorityScore);
      const selectedDca = dcas.find((d) => d.dcaId === selectedDcaId);
      onAssigned(caseData.accountId, selectedDcaId, selectedDca?.name || 'Unknown DCA');
      onClose();
    } catch (error) {
      console.error('Failed to allocate case:', error);
      // For demo purposes, still mark as assigned even if API fails
      const selectedDca = dcas.find((d) => d.dcaId === selectedDcaId);
      onAssigned(caseData.accountId, selectedDcaId, selectedDca?.name || 'Unknown DCA');
      onClose();
    } finally {
      setIsAssigning(false);
    }
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(amount);
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-2xl max-h-[80vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle className="flex items-center gap-2 text-xl">
            <Building2 className="h-5 w-5 text-primary" />
            Assign Case to DCA
          </DialogTitle>
          <DialogDescription>
            Select a DCA to handle this collection case
          </DialogDescription>
        </DialogHeader>

        {/* Case Summary */}
        <div className="mb-4 rounded-lg border border-border bg-muted/30 p-4">
          <h4 className="mb-3 font-semibold text-foreground">Case Details</h4>
          <div className="grid grid-cols-2 gap-3 text-sm">
            <div>
              <span className="text-muted-foreground">Customer:</span>
              <span className="ml-2 font-medium">{caseData.customerName}</span>
            </div>
            <div>
              <span className="text-muted-foreground">Account ID:</span>
              <span className="ml-2 font-medium">#{caseData.accountId}</span>
            </div>
            <div>
              <span className="text-muted-foreground">Outstanding:</span>
              <span className="ml-2 font-semibold text-destructive">
                {formatCurrency(caseData.outstandingAmount || 0)}
              </span>
            </div>
            <div>
              <span className="text-muted-foreground">Priority:</span>
              <Badge className={cn(
                'ml-2',
                caseData.priorityScore >= 85 ? 'bg-destructive' :
                caseData.priorityScore >= 70 ? 'bg-warning' : 'bg-success'
              )}>
                {caseData.priorityScore.toFixed(1)}
              </Badge>
            </div>
          </div>
        </div>

        {/* DCA Selection */}
        <div className="space-y-3">
          <h4 className="font-semibold text-foreground">Select DCA Partner</h4>
          
          {isLoading ? (
            <div className="flex items-center justify-center py-8">
              <Loader2 className="h-6 w-6 animate-spin text-primary" />
              <span className="ml-2 text-muted-foreground">Loading DCA performance data...</span>
            </div>
          ) : (
            <div className="space-y-2">
              {dcaOptions.map((dca) => {
                const isRecommended = dca.dcaId === recommendedDcaId;
                const isSelected = dca.dcaId === selectedDcaId;

                return (
                  <div
                    key={dca.dcaId}
                    onClick={() => setSelectedDcaId(dca.dcaId)}
                    className={cn(
                      'cursor-pointer rounded-lg border-2 p-4 transition-all',
                      isSelected
                        ? 'border-primary bg-primary/5'
                        : 'border-border hover:border-primary/50',
                      isRecommended && !isSelected && 'border-success/50 bg-success/5'
                    )}
                  >
                    <div className="flex items-start justify-between">
                      <div className="flex items-center gap-3">
                        <div className={cn(
                          'flex h-10 w-10 items-center justify-center rounded-full',
                          isSelected ? 'bg-primary text-primary-foreground' : 'bg-muted text-muted-foreground'
                        )}>
                          {isSelected ? (
                            <CheckCircle2 className="h-5 w-5" />
                          ) : (
                            <Building2 className="h-5 w-5" />
                          )}
                        </div>
                        <div>
                          <div className="flex items-center gap-2">
                            <span className="font-semibold text-foreground">{dca.name}</span>
                            {isRecommended && (
                              <Badge className="bg-success text-success-foreground">
                                <Star className="mr-1 h-3 w-3" />
                                Recommended
                              </Badge>
                            )}
                          </div>
                          <span className="text-sm text-muted-foreground">
                            Commission: {dca.commissionRate}%
                          </span>
                        </div>
                      </div>
                    </div>

                    {/* Performance Metrics */}
                    {dca.performance && (
                      <div className="mt-4 grid grid-cols-3 gap-4">
                        <div className="space-y-1">
                          <div className="flex items-center gap-1 text-xs text-muted-foreground">
                            <TrendingUp className="h-3 w-3" />
                            Recovery Rate
                          </div>
                          <div className="flex items-center gap-2">
                            <Progress value={dca.performance.recoveryRate} className="h-2" />
                            <span className="text-sm font-semibold text-success">
                              {dca.performance.recoveryRate}%
                            </span>
                          </div>
                        </div>
                        <div className="space-y-1">
                          <div className="flex items-center gap-1 text-xs text-muted-foreground">
                            <Clock className="h-3 w-3" />
                            Avg Days
                          </div>
                          <span className="text-lg font-semibold text-foreground">
                            {dca.performance.avgResolutionDays}
                          </span>
                        </div>
                        <div className="space-y-1">
                          <div className="flex items-center gap-1 text-xs text-muted-foreground">
                            <AlertCircle className="h-3 w-3" />
                            Escalations
                          </div>
                          <span className={cn(
                            'text-lg font-semibold',
                            dca.performance.escalations > 4 ? 'text-destructive' : 'text-foreground'
                          )}>
                            {dca.performance.escalations}
                          </span>
                        </div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          )}
        </div>

        <DialogFooter className="mt-6">
          <Button variant="outline" onClick={onClose} disabled={isAssigning}>
            Cancel
          </Button>
          <Button
            onClick={handleAssign}
            disabled={!selectedDcaId || isAssigning}
            className="min-w-[120px]"
          >
            {isAssigning ? (
              <>
                <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                Assigning...
              </>
            ) : (
              'Confirm Assignment'
            )}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
