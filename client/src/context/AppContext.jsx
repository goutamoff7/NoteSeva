import { createContext, useContext, useState } from "react";

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  
  const [token,setToken] = useState(localStorage.getItem('token') ? localStorage.getItem('token') : false)

  const value = {
    backendUrl,
    token,
    setToken,
  };

  return (
    <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
  );
};

export const useAppContext = () => useContext(AppContext);
