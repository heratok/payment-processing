import { useState } from 'react';
import { PaymentForm } from './components/PaymentForm';
import { PaymentHistory } from './components/PaymentHistory';
import { motion, AnimatePresence } from 'framer-motion';
import React from 'react';
import { ThemeProvider, useTheme } from './theme/ThemeContext';
import { Sun, Moon } from 'lucide-react';

function ThemeToggle() {
  const { isDarkMode, toggleTheme } = useTheme();
  
  return (
    <motion.button
      whileHover={{ scale: 1.05 }}
      whileTap={{ scale: 0.95 }}
      onClick={toggleTheme}
      className="p-2 border rounded-lg shadow-lg bg-card border-border"
      aria-label="Toggle theme"
    >
      {isDarkMode ? 
        <Sun className="w-5 h-5 text-yellow-400" /> : 
        <Moon className="w-5 h-5 text-slate-700" />
      }
    </motion.button>
  );
}

function AppContent() {
  const [currentView, setCurrentView] = useState<'form' | 'history'>('form');
  const { isDarkMode } = useTheme();

  return (
    <div className="min-h-screen bg-background text-foreground">
      <nav className="border-b bg-card">
        <div className="container px-4 mx-auto sm:px-6 lg:px-8">
          <div className="flex flex-col items-center justify-between py-4 sm:flex-row">
            <div className="flex items-center space-x-4">
              <motion.h1 
                initial={{ opacity: 0, y: -20 }}
                animate={{ opacity: 1, y: 0 }}
                className="text-2xl font-bold sm:text-3xl text-foreground"
              >
                Procesamiento de Pagos
              </motion.h1>
              <ThemeToggle />
            </div>
            
            <div className="flex mt-4 space-x-4 sm:mt-0">
              <motion.button
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                onClick={() => setCurrentView('form')}
                className={`px-4 py-2 rounded-lg transition-all duration-300 ${
                  currentView === 'form' 
                    ? 'bg-primary text-primary-foreground shadow-lg' 
                    : 'bg-secondary text-secondary-foreground hover:bg-secondary/80'
                }`}
              >
                Nuevo Pago
              </motion.button>
              <motion.button
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                onClick={() => setCurrentView('history')}
                className={`px-4 py-2 rounded-lg transition-all duration-300 ${
                  currentView === 'history' 
                    ? 'bg-primary text-primary-foreground shadow-lg' 
                    : 'bg-secondary text-secondary-foreground hover:bg-secondary/80'
                }`}
              >
                Historial
              </motion.button>
            </div>
          </div>
        </div>
      </nav>

      <main className="container p-4 mx-auto sm:p-6 lg:p-8">
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="max-w-4xl p-6 mx-auto border shadow-lg rounded-xl sm:p-8 bg-card border-border"
        >
          <AnimatePresence mode="wait">
            <motion.div
              key={currentView}
              initial={{ opacity: 0, x: currentView === 'form' ? -20 : 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: currentView === 'form' ? 20 : -20 }}
              transition={{ duration: 0.3 }}
            >
              {currentView === 'form' ? <PaymentForm /> : <PaymentHistory />}
            </motion.div>
          </AnimatePresence>
        </motion.div>
      </main>
    </div>
  );
}

function App() {
  return (
    <ThemeProvider>
      <AppContent />
    </ThemeProvider>
  );
}

export default App; 