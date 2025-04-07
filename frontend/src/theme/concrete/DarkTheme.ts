import { Theme } from '../interfaces/Theme';

export class DarkTheme implements Theme {
  background = 'hsl(224 71.4% 4.1%)';
  foreground = 'hsl(210 20% 98%)';
  primary = 'hsl(210 20% 98%)';
  secondary = 'hsl(215 20.2% 65.1%)';
  accent = 'hsl(216 34% 17%)';
  muted = 'hsl(215 27.9% 16.9%)';
  border = 'hsl(216 34% 17%)';

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