import React from 'react';
import { useTheme } from '../theme/ThemeContext';

interface BaseComponentProps {
  children: React.ReactNode;
  className?: string;
}

export const BaseComponent: React.FC<BaseComponentProps> = ({ children, className = '' }) => {
  const { theme } = useTheme();

  return (
    <div className={`bg-${theme.background} text-${theme.text} ${className}`}>
      {children}
    </div>
  );
}; 