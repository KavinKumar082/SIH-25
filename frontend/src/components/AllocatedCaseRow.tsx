import { Badge } from '@/components/ui/badge';
import { TableCell, TableRow } from '@/components/ui/table';
import { Calendar, Target, IndianRupee, Eye } from 'lucide-react';
import { Button } from '@/components/ui/button';
import type { AllocatedCase } from '@/lib/api';
import { cn } from '@/lib/utils';

interface AllocatedCaseRowProps {
  caseData: AllocatedCase;
  onViewDetails?: (caseData: AllocatedCase) => void;
}

const statusConfig = {
  NEW: { label: 'New', className: 'bg-warning text-warning-foreground' },
  RUNNING: { label: 'Running', className: 'bg-primary text-primary-foreground' },
  RESOLVED: { label: 'Resolved', className: 'bg-success text-success-foreground' },
  ESCALATED: { label: 'Escalated', className: 'bg-destructive text-destructive-foreground' },
};

export function AllocatedCaseRow({ caseData, onViewDetails }: AllocatedCaseRowProps) {
  const status = statusConfig[caseData.status];

  const formatDate = (dateStr: string) => {
    return new Date(dateStr).toLocaleDateString('en-IN', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(amount);
  };

  const isOverdue = new Date(caseData.slaDueDate) < new Date() && caseData.status !== 'RESOLVED';

  return (
    <TableRow 
      className={cn(
        "hover:bg-muted/50 cursor-pointer transition-colors",
        onViewDetails && "cursor-pointer"
      )}
      onClick={() => onViewDetails?.(caseData)}
    >
      <TableCell className="font-medium">{caseData.customerName}</TableCell>
      <TableCell>
        <div className="flex items-center gap-1">
          <Target className="h-3 w-3 text-muted-foreground" />
          <span className={cn(
            'font-semibold',
            caseData.priorityScore >= 85 ? 'text-destructive' :
            caseData.priorityScore >= 70 ? 'text-warning' : 'text-success'
          )}>
            {caseData.priorityScore.toFixed(1)}
          </span>
        </div>
      </TableCell>
      <TableCell>
        <div className="flex items-center gap-1 text-sm">
          <IndianRupee className="h-3 w-3 text-destructive" />
          <span className="font-semibold text-destructive">
            {formatCurrency(caseData.outstandingAmount || 0)}
          </span>
        </div>
      </TableCell>
      <TableCell>
        <div className="flex items-center gap-1 text-sm text-muted-foreground">
          <Calendar className="h-3 w-3" />
          {formatDate(caseData.assignedDate)}
        </div>
      </TableCell>
      <TableCell>
        <div className={cn(
          'flex items-center gap-1 text-sm',
          isOverdue ? 'text-destructive font-semibold' : 'text-muted-foreground'
        )}>
          <Calendar className="h-3 w-3" />
          {formatDate(caseData.slaDueDate)}
          {isOverdue && <span className="text-xs">(Overdue)</span>}
        </div>
      </TableCell>
      <TableCell>
        <Badge className={status.className}>
          {status.label}
        </Badge>
      </TableCell>
      <TableCell>
        <Button 
          variant="ghost" 
          size="sm"
          onClick={(e) => {
            e.stopPropagation();
            onViewDetails?.(caseData);
          }}
        >
          <Eye className="h-4 w-4" />
        </Button>
      </TableCell>
    </TableRow>
  );
}
