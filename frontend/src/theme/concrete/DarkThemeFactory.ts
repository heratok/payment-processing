import { ThemeFactory } from '../interfaces/ThemeFactory';
import { Theme } from '../interfaces/Theme';
import { DarkTheme } from './DarkTheme';

export class DarkThemeFactory implements ThemeFactory {
  createBackground(): string {
    return 'hsl(224 71.4% 4.1%)';
  }

  createForeground(): string {
    return 'hsl(210 20% 98%)';
  }

  createPrimary(): string {
    return 'hsl(210 20% 98%)';
  }

  createSecondary(): string {
    return 'hsl(215 20.2% 65.1%)';
  }

  createAccent(): string {
    return 'hsl(216 34% 17%)';
  }

  createMuted(): string {
    return 'hsl(215 27.9% 16.9%)';
  }

  createBorder(): string {
    return 'hsl(216 34% 17%)';
  }

  createTheme(): Theme {
    return new DarkTheme();
  }
} 