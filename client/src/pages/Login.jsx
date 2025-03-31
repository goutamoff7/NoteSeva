import React, { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import axios from "axios";
import { toast } from "react-toastify";
import { useAppContext } from "../context/AppContext";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate()

  const { backendUrl,setToken } = useAppContext();

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const {data} = await axios.post(`${backendUrl}/public/login`, { email, password });
      toast.success("Login successful");
      localStorage.setItem('token', data.token);
      setToken(data.token);
      navigate('/dashboard')
    } catch (error) {
      toast.error("Login failed. Please check your credentials.");
      console.error("Error:", error.response?.data || error.message);
    }
  };

  return (
    <div className="min-h-screen bg-darkbg flex items-center justify-center w-full">
      <div className="min-w-[35%] w-fit bg-[#475569] border-gray-200 p-10 rounded-xl shadow-lg flex flex-col">
        <h2 className="text-3xl text-white font-bold mb-6 text-center">WELCOME BACK</h2>
        <p className="text-white text-center mb-4">Welcome back! Please enter your details.</p>

        <form className="space-y-6" onSubmit={handleLogin}>
          <div>
            <label className="block text-white mb-2" htmlFor="email">Email</label>
            <input
              className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
              type="email"
              id="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="relative w-full">
            <label className="block text-white mb-2" htmlFor="password">Password</label>
            <input
              className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
              type={showPassword ? "text" : "password"}
              id="password"
              placeholder="********"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <div className="absolute inset-y-0 top-12 right-4">
              {showPassword ? (
                <FaEyeSlash className="text-gray-400 cursor-pointer" onClick={togglePasswordVisibility} />
              ) : (
                <FaEye className="text-gray-400 cursor-pointer" onClick={togglePasswordVisibility} />
              )}
            </div>
          </div>

          <div className="flex justify-between items-center">
            <div className="flex items-center">
              <input type="checkbox" id="remember-me" className="cursor-pointer" />
              <label htmlFor="remember-me" className="text-white ml-2 cursor-pointer">Remember me</label>
            </div>
            <a href="forgot-password" className="text-white hover:text-btngreen">Forgot password</a>
          </div>

          <button type="submit" className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors">
            Sign in
          </button>
        </form>

        <div className="flex justify-center">
          <button className="mt-4 w-full flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg">
            <FcGoogle className="w-[30px] h-[30px]" /> Google
          </button>
        </div>

        <p className="mt-6 text-gray-400">
          Don’t have an account?
          <a href="./signup" className="text-green-500 hover:text-green-600"> Sign up for free!</a>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
