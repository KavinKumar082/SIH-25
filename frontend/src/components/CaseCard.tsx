import { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { User, MapPin, AlertCircle, Clock, Target, IndianRupee, Hash, Building2 } from 'lucide-react';
import { cn } from '@/lib/utils';
import type { CaseData } from '@/lib/api';
import { DCAAssignmentModal } from './DCAAssignmentModal';

interface CaseCardProps {
  caseData: CaseData;
  recommendedDcaId?: number;
  onAssigned?: (accountId: number, dcaId: number) => void;
}

export function CaseCard({ caseData, recommendedDcaId, onAssigned }: CaseCardProps) {
  const [isAssigned, setIsAssigned] = useState(caseData.isAssigned);
  const [assignedDcaName, setAssignedDcaName] = useState(caseData.assignedDcaName || '');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const getPriorityColor = (score: number) => {
    if (score >= 85) return 'bg-priority-high text-destructive-foreground';
    if (score >= 70) return 'bg-priority-medium text-warning-foreground';
    return 'bg-priority-low text-success-foreground';
  };

  const getAgingColor = (bucket: string) => {
    if (bucket.includes('90+')) return 'border-destructive text-destructive';
    if (bucket.includes('60-90')) return 'border-warning text-warning';
    return 'border-muted-foreground text-muted-foreground';
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(amount);
  };

  const handleAssigned = (accountId: number, dcaId: number, dcaName: string) => {
    setIsAssigned(true);
    setAssignedDcaName(dcaName);
    onAssigned?.(accountId, dcaId);
  };

  return (
    <>
      <div className={cn(
        'dashboard-card p-4 animate-fade-in',
        isAssigned && 'border-success/50 bg-success/5'
      )}>
        {/* Header with Customer Info & Priority */}
        <div className="flex items-start justify-between">
          <div className="flex items-center gap-3">
            <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10 text-primary">
              <User className="h-6 w-6" />
            </div>
            <div>
              <h4 className="font-semibold text-foreground">{caseData.customerName}</h4>
              <div className="mt-1 flex items-center gap-3 text-sm text-muted-foreground">
                <span className="flex items-center gap-1">
                  <Hash className="h-3 w-3" />
                  {caseData.accountId}
                </span>
                <span className="flex items-center gap-1">
                  <MapPin className="h-3 w-3" />
                  {caseData.location}
                </span>
              </div>
            </div>
          </div>
          <Badge className={cn('text-sm font-bold', getPriorityColor(caseData.priorityScore))}>
            <Target className="mr-1 h-3 w-3" />
            {caseData.priorityScore.toFixed(1)}
          </Badge>
        </div>

        {/* Case Details Grid */}
        <div className="mt-4 grid grid-cols-2 gap-3 rounded-lg bg-muted/30 p-3">
          <div className="flex items-center gap-2 text-sm">
            <IndianRupee className="h-4 w-4 text-destructive" />
            <span className="text-muted-foreground">Outstanding:</span>
            <span className="font-bold text-destructive">
              {formatCurrency(caseData.outstandingAmount || 0)}
            </span>
          </div>
          <div className="flex items-center gap-2 text-sm">
            <AlertCircle className="h-4 w-4 text-muted-foreground" />
            <span className="text-muted-foreground">Delinquency:</span>
            <span className="font-semibold">{caseData.delinquencyScore}</span>
          </div>
          <div className="col-span-2 flex items-center gap-2 text-sm">
            <Clock className="h-4 w-4 text-muted-foreground" />
            <span className="text-muted-foreground">Aging:</span>
            <Badge variant="outline" className={getAgingColor(caseData.agingBucket)}>
              {caseData.agingBucket}
            </Badge>
          </div>
        </div>

        {/* Assigned DCA Info (if assigned) */}
        {isAssigned && assignedDcaName && (
          <div className="mt-3 flex items-center gap-2 rounded-lg bg-success/10 p-2 text-sm">
            <Building2 className="h-4 w-4 text-success" />
            <span className="text-muted-foreground">Assigned to:</span>
            <span className="font-semibold text-success">{assignedDcaName}</span>
            <Badge className="ml-auto bg-warning text-warning-foreground">
              🟠 New
            </Badge>
          </div>
        )}

        {/* Assignment Button */}
        <div className="mt-4">
          <Button
            onClick={() => setIsModalOpen(true)}
            disabled={isAssigned}
            className={cn(
              'w-full transition-all',
              isAssigned
                ? 'bg-success hover:bg-success text-success-foreground'
                : 'bg-destructive hover:bg-destructive/90'
            )}
          >
            {isAssigned ? '✓ Assigned' : '● Unassigned'}
          </Button>
        </div>
      </div>

      {/* Assignment Modal */}
      <DCAAssignmentModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        caseData={caseData}
        recommendedDcaId={recommendedDcaId}
        onAssigned={handleAssigned}
      />
    </>
  );
}
