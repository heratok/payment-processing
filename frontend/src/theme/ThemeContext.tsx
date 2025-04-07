import React, { createContext, useContext, useState, useEffect } from 'react';
import { Theme } from './interfaces/Theme';
import { ThemeFactory } from './interfaces/ThemeFactory';
import { LightThemeFactory } from './concrete/LightThemeFactory';
import { DarkThemeFactory } from './concrete/DarkThemeFactory';

interface ThemeContextType {
  isDarkMode: boolean;
  toggleTheme: () => void;
  theme: Theme;
}

const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

export const ThemeProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isDarkMode, setIsDarkMode] = useState(() => {
    const savedTheme = localStorage.getItem('theme');
    return savedTheme ? savedTheme === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches;
  });

  const [themeFactory, setThemeFactory] = useState<ThemeFactory>(
    isDarkMode ? new DarkThemeFactory() : new LightThemeFactory()
  );

  const [theme, setTheme] = useState<Theme>(themeFactory.createTheme());

  useEffect(() => {
    const newFactory = isDarkMode ? new DarkThemeFactory() : new LightThemeFactory();
    setThemeFactory(newFactory);
    setTheme(newFactory.createTheme());
    
    document.documentElement.classList.toggle('dark', isDarkMode);
    localStorage.setItem('theme', isDarkMode ? 'dark' : 'light');
  }, [isDarkMode]);

  const toggleTheme = () => {
    setIsDarkMode(prev => !prev);
  };

  return (
    <ThemeContext.Provider value={{ isDarkMode, toggleTheme, theme }}>
      {children}
    </ThemeContext.Provider>
  );
};

export const useTheme = () => {
  const context = useContext(ThemeContext);
  if (context === undefined) {
    throw new Error('useTheme must be used within a ThemeProvider');
  }
  return context;
}; 