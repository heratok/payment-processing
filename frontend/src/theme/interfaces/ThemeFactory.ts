import { Theme } from './Theme';

export interface ThemeFactory {
  createBackground(): string;
  createForeground(): string;
  createPrimary(): string;
  createSecondary(): string;
  createAccent(): string;
  createMuted(): string;
  createBorder(): string;
  createTheme(): Theme;
} 