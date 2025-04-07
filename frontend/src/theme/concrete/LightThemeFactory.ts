import { ThemeFactory } from '../interfaces/ThemeFactory';
import { Theme } from '../interfaces/Theme';
import { LightTheme } from './LightTheme';

export class LightThemeFactory implements ThemeFactory {
  createBackground(): string {
    return 'hsl(0 0% 100%)';
  }

  createForeground(): string {
    return 'hsl(224 71.4% 4.1%)';
  }

  createPrimary(): string {
    return 'hsl(220.9 39.3% 11%)';
  }

  createSecondary(): string {
    return 'hsl(215 20.2% 65.1%)';
  }

  createAccent(): string {
    return 'hsl(220 14.3% 95.9%)';
  }

  createMuted(): string {
    return 'hsl(220 14.3% 95.9%)';
  }

  createBorder(): string {
    return 'hsl(220 13% 91%)';
  }

  createTheme(): Theme {
    return new LightTheme();
  }
} 