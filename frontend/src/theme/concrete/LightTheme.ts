import { Theme } from '../interfaces/Theme';

export class LightTheme implements Theme {
  background = 'hsl(0 0% 100%)';
  foreground = 'hsl(224 71.4% 4.1%)';
  primary = 'hsl(220.9 39.3% 11%)';
  secondary = 'hsl(215 20.2% 65.1%)';
  accent = 'hsl(220 14.3% 95.9%)';
  muted = 'hsl(220 14.3% 95.9%)';
  border = 'hsl(220 13% 91%)';

  getClassName(element: string): string {
    switch (element) {
      case 'background':
        return 'bg-background';
      case 'foreground':
        return 'text-foreground';
      case 'primary':
        return 'bg-primary text-primary-foreground';
      case 'secondary':
        return 'bg-secondary text-secondary-foreground';
      case 'accent':
        return 'bg-accent text-accent-foreground';
      case 'muted':
        return 'bg-muted text-muted-foreground';
      default:
        return '';
    }
  }
} 