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
      primary: 'bg-blue-600',
      secondary: 'bg-green-500',
      background: 'bg-white',
      text: 'text-gray-800'
    };
  }
}

export class DarkThemeFactory implements ThemeFactory {
  createTheme(): Theme {
    return {
      primary: 'bg-blue-800',
      secondary: 'bg-green-700',
      background: 'bg-gray-800',
      text: 'text-white'
    };
  }
} 