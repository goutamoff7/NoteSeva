// utils/PublicRoute.jsx
import { useAppContext } from "../context/AppContext";
import { Navigate, Outlet } from "react-router-dom";
import { useEffect } from "react";
import { toast } from "react-toastify";

const PublicRoute = () => {
  const { isAuthenticated } = useAppContext();

  if (isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};

export default PublicRoute;
