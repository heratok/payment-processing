import React, { useState } from 'react';
import { paymentService, PaymentRequest } from '../services/api';
import { motion } from 'framer-motion';

export const PaymentForm: React.FC = () => {
  const [formData, setFormData] = useState<PaymentRequest>({
    amount: 0,
    paymentMethod: 'creditcard'
  });
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<{ success: boolean; message: string } | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'amount' ? (value === '' ? 0 : Number(value)) : value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setResult(null);

    try {
      const response = await paymentService.processPayment(formData);
      setResult({
        success: response.success,
        message: response.message
      });
      // Limpiar el formulario después de un pago exitoso
      if (response.success) {
        setFormData({
          amount: 0,
          paymentMethod: 'creditcard'
        });
      }
    } catch (error) {
      setResult({
        success: false,
        message: 'Error al procesar el pago'
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="space-y-6"
    >
      <h2 className="text-2xl font-bold text-gray-800">Nuevo Pago</h2>
      
      <form onSubmit={handleSubmit} className="space-y-6">
        <motion.div
          initial={{ opacity: 0, x: -20 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ delay: 0.1 }}
          className="space-y-2"
        >
          <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
            Monto
          </label>
          <input
            type="number"
            id="amount"
            name="amount"
            value={formData.amount || ''}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition-all duration-300"
            placeholder="Ingrese el monto"
            required
            min="0"
            step="0.01"
          />
        </motion.div>

        <motion.div
          initial={{ opacity: 0, x: -20 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ delay: 0.2 }}
          className="space-y-2"
        >
          <label htmlFor="paymentMethod" className="block text-sm font-medium text-gray-700">
            Método de Pago
          </label>
          <select
            id="paymentMethod"
            name="paymentMethod"
            value={formData.paymentMethod}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition-all duration-300"
          >
            <option value="creditcard">Tarjeta de Crédito</option>
            <option value="debitcard">Tarjeta de Débito</option>
            <option value="paypal">PayPal</option>
          </select>
        </motion.div>

        <motion.button
          whileHover={{ scale: 1.02 }}
          whileTap={{ scale: 0.98 }}
          type="submit"
          disabled={loading}
          className={`w-full py-3 px-4 rounded-lg text-white font-medium transition-all duration-300 ${
            loading 
              ? 'bg-gray-400 cursor-not-allowed' 
              : 'bg-indigo-600 hover:bg-indigo-700 shadow-lg'
          }`}
        >
          {loading ? (
            <div className="flex items-center justify-center">
              <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white mr-2"></div>
              Procesando...
            </div>
          ) : (
            'Procesar Pago'
          )}
        </motion.button>
      </form>

      {result && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className={`p-4 rounded-lg ${
            result.success ? 'bg-green-50 text-green-800' : 'bg-red-50 text-red-800'
          }`}
        >
          <p className="text-center font-medium">{result.message}</p>
        </motion.div>
      )}
    </motion.div>
  );
}; 