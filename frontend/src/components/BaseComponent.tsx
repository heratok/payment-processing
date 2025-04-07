import React from 'react';
import { useTheme } from '../theme/ThemeContext';

interface BaseComponentProps {
  children: React.ReactNode;
  className?: string;
}

export const BaseComponent: React.FC<BaseComponentProps> = ({ children, className = '' }) => {
  const { isDarkMode } = useTheme();

  return (
    <div className={`bg-background text-foreground ${className}`}>
      {children}
    </div>
  );
}; 