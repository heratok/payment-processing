import React from 'react';
import { motion } from 'framer-motion';
import { Mail, MessageSquare, Share2 } from 'lucide-react';

interface InvoiceModalProps {
  isOpen: boolean;
  onClose: () => void;
  paymentData: {
    amount: number;
    paymentMethod: string;
    date: string;
    transactionId: string;
  };
}

export const InvoiceModal: React.FC<InvoiceModalProps> = ({ isOpen, onClose, paymentData }) => {
  if (!isOpen) return null;

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="fixed inset-0 z-50 flex items-center justify-center bg-background/80 backdrop-blur-sm"
    >
      <motion.div
        initial={{ scale: 0.9, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        exit={{ scale: 0.9, opacity: 0 }}
        className="relative w-full max-w-md p-6 bg-card rounded-xl shadow-lg border border-border"
      >
        {/* Encabezado */}
        <div className="text-center mb-6">
          <h2 className="text-2xl font-bold text-foreground">Factura de Pago</h2>
          <p className="text-muted-foreground">Transacción #{paymentData.transactionId}</p>
        </div>

        {/* Detalles de la factura */}
        <div className="space-y-4 mb-6">
          <div className="flex justify-between items-center">
            <span className="text-muted-foreground">Monto:</span>
            <span className="font-semibold text-foreground">${paymentData.amount.toFixed(2)}</span>
          </div>
          <div className="flex justify-between items-center">
            <span className="text-muted-foreground">Método de Pago:</span>
            <span className="font-semibold text-foreground">{paymentData.paymentMethod}</span>
          </div>
          <div className="flex justify-between items-center">
            <span className="text-muted-foreground">Fecha:</span>
            <span className="font-semibold text-foreground">{paymentData.date}</span>
          </div>
        </div>

        {/* Botones de compartir */}
        <div className="grid grid-cols-3 gap-3">
          <motion.button
            whileHover={{ scale: 1.02 }}
            whileTap={{ scale: 0.98 }}
            className="flex flex-col items-center justify-center gap-2 p-4 rounded-lg bg-primary text-primary-foreground hover:opacity-90 transition-all"
          >
            <Mail className="w-5 h-5" />
            <span className="text-sm">Gmail</span>
          </motion.button>

          <motion.button
            whileHover={{ scale: 1.02 }}
            whileTap={{ scale: 0.98 }}
            className="flex flex-col items-center justify-center gap-2 p-4 rounded-lg bg-green-500 text-white hover:opacity-90 transition-all"
          >
            <MessageSquare className="w-5 h-5" />
            <span className="text-sm">WhatsApp</span>
          </motion.button>

          <motion.button
            whileHover={{ scale: 1.02 }}
            whileTap={{ scale: 0.98 }}
            className="flex flex-col items-center justify-center gap-2 p-4 rounded-lg bg-blue-500 text-white hover:opacity-90 transition-all"
          >
            <Share2 className="w-5 h-5" />
            <span className="text-sm">SMS</span>
          </motion.button>
        </div>

        {/* Botón de cerrar */}
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-muted-foreground hover:text-foreground transition-colors"
        >
          ✕
        </button>
      </motion.div>
    </motion.div>
  );
}; 