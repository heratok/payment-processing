export interface Theme {
  primary: string;
  secondary: string;
  background: string;
  text: string;
}

export interface ThemeFactory {
  createTheme(): Theme;
}

export class LightThemeFactory implements ThemeFactory {
  createTheme(): Theme {
    return {
      primary: 'bg-primary-light',
      secondary: 'bg-secondary-light',
      background: 'bg-background-light',
      text: 'text-text-light'
    };
  }
}

export class DarkThemeFactory implements ThemeFactory {
  createTheme(): Theme {
    return {
      primary: 'bg-primary-dark',
      secondary: 'bg-secondary-dark',
      background: 'bg-background-dark',
      text: 'text-text-dark'
    };
  }
} 