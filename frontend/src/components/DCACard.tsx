import { Link } from 'react-router-dom';
import { Building, Percent, ChevronRight } from 'lucide-react';
import type { DCA } from '@/lib/api';

interface DCACardProps {
  dca: DCA;
}

export function DCACard({ dca }: DCACardProps) {
  return (
    <Link
      to={`/fedex/dca/${dca.dcaId}`}
      className="dashboard-card group flex items-center justify-between p-4 transition-all hover:border-primary"
    >
      <div className="flex items-center gap-4">
        <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-primary/10 text-primary">
          <Building className="h-6 w-6" />
        </div>
        <div>
          <h3 className="font-semibold text-foreground group-hover:text-primary">
            {dca.name}
          </h3>
          <div className="mt-1 flex items-center gap-1 text-sm text-muted-foreground">
            <Percent className="h-3 w-3" />
            <span>{dca.commissionRate}% Commission</span>
          </div>
        </div>
      </div>
      <ChevronRight className="h-5 w-5 text-muted-foreground transition-transform group-hover:translate-x-1 group-hover:text-primary" />
    </Link>
  );
}
