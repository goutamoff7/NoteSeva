import { useAppContext } from "../context/AppContext";
import { Navigate, Outlet } from "react-router-dom";
import { toast } from "react-toastify";

const ProtectedRoute = () => {
  const { isAuthenticated } = useAppContext();

  if (!isAuthenticated) {
    toast.info("Please login");
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
