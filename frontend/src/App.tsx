import { useState } from 'react';
import { PaymentForm } from './components/PaymentForm';
import { PaymentHistory } from './components/PaymentHistory';
import { motion, AnimatePresence } from 'framer-motion';
import React from 'react';

function App() {
  const [currentView, setCurrentView] = useState<'form' | 'history'>('form');

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 to-purple-50">
      <nav className="bg-white shadow-lg">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex flex-col sm:flex-row justify-between items-center py-4">
            <motion.h1 
              initial={{ opacity: 0, y: -20 }}
              animate={{ opacity: 1, y: 0 }}
              className="text-2xl sm:text-3xl font-bold bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent mb-4 sm:mb-0"
            >
              Procesamiento de Pagos
            </motion.h1>
            <div className="flex space-x-2 sm:space-x-4">
              <motion.button
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                onClick={() => setCurrentView('form')}
                className={`px-4 py-2 rounded-lg transition-all duration-300 ${
                  currentView === 'form' 
                    ? 'bg-indigo-600 text-white shadow-lg' 
                    : 'bg-white text-indigo-600 hover:bg-indigo-50'
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
                    ? 'bg-indigo-600 text-white shadow-lg' 
                    : 'bg-white text-indigo-600 hover:bg-indigo-50'
                }`}
              >
                Historial
              </motion.button>
            </div>
          </div>
        </div>
      </nav>

      <main className="container mx-auto p-4 sm:p-6 lg:p-8">
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="max-w-4xl mx-auto bg-white rounded-xl shadow-lg p-6 sm:p-8"
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

export default App; 