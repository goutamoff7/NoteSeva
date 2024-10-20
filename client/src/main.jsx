// src/main.jsx
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import store from './store/store'; // Import the Redux store
import App from './App';
import 'react-toastify/dist/ReactToastify.css'; // Import the CSS for Toastify
import './index.css';  // Adjust the path according to your project structure

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <App />
        <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} />
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
);
