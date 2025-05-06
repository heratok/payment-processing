import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { FileText, Settings } from 'lucide-react';

interface ReportFormatModalProps {
  isOpen: boolean;
  onClose: () => void;
  onGenerateReport: (options: any) => void;
  paymentData: {
    amount: number;
    paymentMethod: string;
    date: string;
    transactionId: string;
    status: string;
  };
}

export const ReportFormatModal: React.FC<ReportFormatModalProps> = ({
  isOpen,
  onClose,
  onGenerateReport,
  paymentData
}) => {
  const [selectedFormat, setSelectedFormat] = useState<'standard' | 'custom'>('standard');
  const [isTransitioning, setIsTransitioning] = useState(false);
  const [customOptions, setCustomOptions] = useState({
    includeLogo: true,
    includePaymentDetails: true,
    includeUserInfo: true,
    includeTimestamp: true,
    theme: 'LIGHT' as const,
    format: 'A4' as const
  });

  const handleGenerate = () => {
    if (selectedFormat === 'standard') {
      onGenerateReport({
        title: 'Reporte de Pago',
        theme: 'LIGHT',
        includeLogo: true,
        includePaymentDetails: true,
        includeUserInfo: true,
        includeTimestamp: true,
        format: 'A4',
        paymentData
      });
    } else {
      onGenerateReport({
        title: 'Reporte de Pago Personalizado',
        theme: customOptions.theme,
        includeLogo: customOptions.includeLogo,
        includePaymentDetails: customOptions.includePaymentDetails,
        includeUserInfo: customOptions.includeUserInfo,
        includeTimestamp: customOptions.includeTimestamp,
        format: customOptions.format,
        paymentData
      });
    }
    onClose();
  };

  if (!isOpen) return null;

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="flex fixed inset-0 z-50 justify-center items-center backdrop-blur-sm bg-background/80"
    >
      <motion.div
        initial={{ scale: 0.9, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        exit={{ scale: 0.9, opacity: 0 }}
        className="relative p-6 w-full max-w-md rounded-xl border shadow-lg bg-card border-border"
      >
        <h2 className="mb-6 text-2xl font-bold text-center text-foreground">
          Formato del Reporte
        </h2>

        <div className="mb-6 space-y-4">
          <div className="flex gap-4">
            <button
              onClick={() => {
                if (!isTransitioning) {
                  setIsTransitioning(true);
                  setSelectedFormat('standard');
                  setTimeout(() => setIsTransitioning(false), 300);
                }
              }}
              className={`flex-1 p-4 rounded-lg border transition-all flex flex-col items-center justify-center ${
                selectedFormat === 'standard'
                  ? 'border-primary bg-primary/10 text-primary dark:bg-primary/30 dark:text-primary dark:border-primary'
                  : 'border-border hover:border-primary/50 text-foreground bg-gray-50 hover:bg-gray-100 dark:bg-transparent dark:hover:bg-primary/10 dark:hover:text-primary'
              }`}
            >
              <FileText className="mb-2 w-6 h-6" />
              <span className="text-sm font-medium">Formato Estándar</span>
            </button>

            <button
              onClick={() => {
                if (!isTransitioning) {
                  setIsTransitioning(true);
                  setSelectedFormat('custom');
                  setTimeout(() => setIsTransitioning(false), 300);
                }
              }}
              className={`flex-1 p-4 rounded-lg border transition-all flex flex-col items-center justify-center ${
                selectedFormat === 'custom'
                  ? 'border-primary bg-primary/10 text-primary dark:bg-primary/30 dark:text-primary dark:border-primary'
                  : 'border-border hover:border-primary/50 text-foreground bg-gray-50 hover:bg-gray-100 dark:bg-transparent dark:hover:bg-primary/10 dark:hover:text-primary'
              }`}
            >
              <Settings className="mb-2 w-6 h-6" />
              <span className="text-sm font-medium">Personalizado</span>
            </button>
          </div>

          <motion.div
            initial={false}
            animate={{ height: selectedFormat === 'custom' ? 'auto' : 0, opacity: selectedFormat === 'custom' ? 1 : 0 }}
            transition={{ duration: 0.3, ease: 'easeInOut' }}
            style={{ overflow: 'hidden' }}
          >
            <div className="p-4 space-y-4 rounded-lg border border-border">
              <div className="space-y-2">
                <label className="flex gap-2 items-center">
                  <input
                    type="checkbox"
                    checked={customOptions.includeLogo}
                    onChange={(e) => setCustomOptions(prev => ({ ...prev, includeLogo: e.target.checked }))}
                    className="w-4 h-4 rounded border-border"
                  />
                  Incluir Logo
                </label>

                <label className="flex gap-2 items-center">
                  <input
                    type="checkbox"
                    checked={customOptions.includePaymentDetails}
                    onChange={(e) => setCustomOptions(prev => ({ ...prev, includePaymentDetails: e.target.checked }))}
                    className="w-4 h-4 rounded border-border"
                  />
                  Incluir Detalles de Pago
                </label>

                <label className="flex gap-2 items-center">
                  <input
                    type="checkbox"
                    checked={customOptions.includeUserInfo}
                    onChange={(e) => setCustomOptions(prev => ({ ...prev, includeUserInfo: e.target.checked }))}
                    className="w-4 h-4 rounded border-border"
                  />
                  Incluir Información de Usuario
                </label>

                <label className="flex gap-2 items-center">
                  <input
                    type="checkbox"
                    checked={customOptions.includeTimestamp}
                    onChange={(e) => setCustomOptions(prev => ({ ...prev, includeTimestamp: e.target.checked }))}
                    className="w-4 h-4 rounded border-border"
                  />
                  Incluir Fecha y Hora
                </label>
              </div>

              <div className="space-y-2">
                <label className="block text-sm font-medium">Tema</label>
                <select
                  value={customOptions.theme}
                  onChange={(e) => setCustomOptions(prev => ({ ...prev, theme: e.target.value as 'LIGHT' }))}
                  className="p-2 w-full rounded-lg border border-border bg-card text-foreground"
                >
                  <option value="LIGHT" className="bg-card text-foreground">Claro</option>
                  <option value="DARK" className="bg-card text-foreground">Oscuro</option>
                </select>
              </div>

              <div className="space-y-2">
                <label className="block text-sm font-medium">Formato</label>
                <select
                  value={customOptions.format}
                  onChange={(e) => setCustomOptions(prev => ({ ...prev, format: e.target.value as 'A4' }))}
                  className="p-2 w-full rounded-lg border border-border bg-card text-foreground"
                >
                  <option value="A4" className="bg-card text-foreground">A4</option>
                  <option value="LETTER" className="bg-card text-foreground">Carta</option>
                </select>
              </div>
            </div>
          </motion.div>
        </div>

        <div className="flex gap-4 justify-end">
          <button
            onClick={onClose}
            className="px-4 py-2 text-sm font-medium text-white rounded-lg transition-colors text-muted-foreground hover:text-foreground"
          >
            Cancelar
          </button>
          <button
            onClick={handleGenerate}
            className="px-4 py-2 text-sm font-medium text-white rounded-lg transition-colors bg-primary hover:bg-primary/90"
          >
            Generar Reporte
          </button>
        </div>

        <button
          onClick={onClose}
          className="absolute top-4 right-4 transition-colors text-muted-foreground hover:text-foreground dark:text-muted-foreground/90 dark:hover:text-foreground/90"
        >
          ✕
        </button>
      </motion.div>
    </motion.div>
  );
};