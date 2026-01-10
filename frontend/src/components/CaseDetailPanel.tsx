import { Sheet, SheetContent, SheetHeader, SheetTitle, SheetDescription } from '@/components/ui/sheet';
import { Badge } from '@/components/ui/badge';
import { Separator } from '@/components/ui/separator';
import { 
  User, 
  MapPin, 
  Hash, 
  IndianRupee, 
  Target, 
  AlertCircle, 
  Calendar, 
  Clock,
  FileText
} from 'lucide-react';
import type { AllocatedCase } from '@/lib/api';
import { cn } from '@/lib/utils';

interface CaseDetailPanelProps {
  isOpen: boolean;
  onClose: () => void;
  caseData: AllocatedCase | null;
}

const statusConfig = {
  NEW: { label: 'New', className: 'bg-warning text-warning-foreground' },
  RUNNING: { label: 'Running', className: 'bg-primary text-primary-foreground' },
  RESOLVED: { label: 'Resolved', className: 'bg-success text-success-foreground' },
  ESCALATED: { label: 'Escalated', className: 'bg-destructive text-destructive-foreground' },
};

export function CaseDetailPanel({ isOpen, onClose, caseData }: CaseDetailPanelProps) {
  if (!caseData) return null;

  const status = statusConfig[caseData.status];

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(amount);
  };

  const formatDate = (dateStr: string) => {
    return new Date(dateStr).toLocaleDateString('en-IN', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });
  };

  const isOverdue = new Date(caseData.slaDueDate) < new Date() && caseData.status !== 'RESOLVED';
  const daysUntilSLA = Math.ceil(
    (new Date(caseData.slaDueDate).getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)
  );

  return (
    <Sheet open={isOpen} onOpenChange={onClose}>
      <SheetContent className="w-[400px] sm:w-[540px] overflow-y-auto">
        <SheetHeader>
          <SheetTitle className="flex items-center gap-2">
            <FileText className="h-5 w-5 text-primary" />
            Case Details
          </SheetTitle>
          <SheetDescription>
            Complete information for this collection case
          </SheetDescription>
        </SheetHeader>

        <div className="mt-6 space-y-6">
          {/* Customer Header */}
          <div className="flex items-center gap-4">
            <div className="flex h-16 w-16 items-center justify-center rounded-full bg-primary/10 text-primary">
              <User className="h-8 w-8" />
            </div>
            <div>
              <h3 className="text-xl font-bold text-foreground">{caseData.customerName}</h3>
              <div className="flex items-center gap-2 text-sm text-muted-foreground">
                <Hash className="h-3 w-3" />
                Account #{caseData.accountId}
              </div>
            </div>
          </div>

          <Separator />

          {/* Status Badge */}
          <div className="flex items-center justify-between">
            <span className="text-sm font-medium text-muted-foreground">Current Status</span>
            <Badge className={cn('text-sm', status.className)}>
              {status.label}
            </Badge>
          </div>

          {/* Key Metrics */}
          <div className="grid grid-cols-2 gap-4">
            <div className="rounded-lg border border-border bg-muted/30 p-4">
              <div className="flex items-center gap-2 text-sm text-muted-foreground">
                <IndianRupee className="h-4 w-4" />
                Outstanding Amount
              </div>
              <p className="mt-1 text-2xl font-bold text-destructive">
                {formatCurrency(caseData.outstandingAmount || 0)}
              </p>
            </div>

            <div className="rounded-lg border border-border bg-muted/30 p-4">
              <div className="flex items-center gap-2 text-sm text-muted-foreground">
                <Target className="h-4 w-4" />
                Priority Score
              </div>
              <p className={cn(
                'mt-1 text-2xl font-bold',
                caseData.priorityScore >= 85 ? 'text-destructive' :
                caseData.priorityScore >= 70 ? 'text-warning' : 'text-success'
              )}>
                {caseData.priorityScore.toFixed(1)}
              </p>
            </div>
          </div>

          <Separator />

          {/* Location & Delinquency */}
          <div className="space-y-4">
            <h4 className="font-semibold text-foreground">Customer Information</h4>
            
            <div className="space-y-3">
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2 text-sm text-muted-foreground">
                  <MapPin className="h-4 w-4" />
                  Location
                </div>
                <span className="font-medium">{caseData.location || 'Not specified'}</span>
              </div>

              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2 text-sm text-muted-foreground">
                  <AlertCircle className="h-4 w-4" />
                  Delinquency Score
                </div>
                <span className={cn(
                  'font-semibold',
                  (caseData.delinquencyScore || 0) >= 85 ? 'text-destructive' :
                  (caseData.delinquencyScore || 0) >= 70 ? 'text-warning' : 'text-foreground'
                )}>
                  {caseData.delinquencyScore || 'N/A'}
                </span>
              </div>
            </div>
          </div>

          <Separator />

          {/* SLA Information */}
          <div className="space-y-4">
            <h4 className="font-semibold text-foreground">SLA Information</h4>
            
            <div className="space-y-3">
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2 text-sm text-muted-foreground">
                  <Calendar className="h-4 w-4" />
                  Assigned Date
                </div>
                <span className="font-medium">{formatDate(caseData.assignedDate)}</span>
              </div>

              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2 text-sm text-muted-foreground">
                  <Clock className="h-4 w-4" />
                  SLA Due Date
                </div>
                <span className={cn(
                  'font-medium',
                  isOverdue && 'text-destructive'
                )}>
                  {formatDate(caseData.slaDueDate)}
                </span>
              </div>

              {/* SLA Status Card */}
              <div className={cn(
                'rounded-lg p-4 text-center',
                isOverdue 
                  ? 'bg-destructive/10 border border-destructive/30'
                  : daysUntilSLA <= 7
                  ? 'bg-warning/10 border border-warning/30'
                  : 'bg-success/10 border border-success/30'
              )}>
                {isOverdue ? (
                  <>
                    <p className="text-sm font-medium text-destructive">SLA Overdue</p>
                    <p className="text-2xl font-bold text-destructive">
                      {Math.abs(daysUntilSLA)} days past
                    </p>
                  </>
                ) : (
                  <>
                    <p className="text-sm font-medium text-muted-foreground">Days Remaining</p>
                    <p className={cn(
                      'text-2xl font-bold',
                      daysUntilSLA <= 7 ? 'text-warning' : 'text-success'
                    )}>
                      {daysUntilSLA} days
                    </p>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </SheetContent>
    </Sheet>
  );
}
