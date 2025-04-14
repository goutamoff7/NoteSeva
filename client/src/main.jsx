import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import App from './App';
import 'react-toastify/dist/ReactToastify.css';
import './index.css'; 
import { AppProvider } from './context/AppContext';
import { AllProvider } from './context/AllContext';

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AppProvider>
          <AllProvider>
            <App />
            <ToastContainer position="top-center" autoClose={3000} hideProgressBar={false} />
          </AllProvider>
        </AppProvider>
      </BrowserRouter>
    </QueryClientProvider>
  </React.StrictMode>
);
