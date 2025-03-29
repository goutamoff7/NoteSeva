import { useContext, useState } from "react";
import { FiEye, FiEyeOff } from "react-icons/fi";
import { toast } from "react-toastify";
import axios from "axios";
import {useAppContext } from "../context/AppContext";

const ChangePassword = () => {
  const [oldPassword, setOldPassword] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [isOldPasswordVisible, setIsOldPasswordVisible] = useState(false);
  const [isConfirmPasswordVisible, setIsConfirmPasswordVisible] = useState(false);

  const {backendUrl} = useAppContext()

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password.length < 8) {
      toast.error("Password must be at least 8 characters long!");
      return;
    }

    if (oldPassword === password) {
      toast.error("New password cannot be the same as the old password!");
      return;
    }

    if (password !== confirmPassword) {
      toast.error("New password and confirm password do not match!");
      return;
    }

    try {
      const response = await axios.post(`${backendUrl}/change-password`, {
        oldPassword,
        password,
        confirmPassword,
      });

      toast.success(response.data.message);
      setOldPassword("");
      setPassword("");
      setConfirmPassword("");
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to change password");
    }
  };

  return (
    <div className="bg-darkbg w-full h-screen flex justify-center items-center">
      <div className="max-w-md min-w-[30%] shadow-xl border bg-[#475569] rounded-2xl overflow-hidden p-8">
        <h2 className="text-3xl font-bold mb-6 text-center text-btngreen">
          Change Password
        </h2>

        <form onSubmit={handleSubmit}>
          {/* Old Password Input */}
          <div className="relative mb-4">
            <input
              type={isOldPasswordVisible ? "text" : "password"}
              placeholder="Old Password"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              required
              className="w-full p-3 bg-gray-800 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 pr-12"
            />
            <button
              type="button"
              onClick={() => setIsOldPasswordVisible(!isOldPasswordVisible)}
              className="absolute inset-y-0 right-4 flex items-center text-gray-500"
            >
              {isOldPasswordVisible ? <FiEyeOff size={20} /> : <FiEye size={20} />}
            </button>
          </div>

          {/* New Password Input */}
          <div className="relative mb-4">
            <input
              type={isPasswordVisible ? "text" : "password"}
              placeholder="New Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full p-3 bg-gray-800 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 pr-12"
            />
            <button
              type="button"
              onClick={() => setIsPasswordVisible(!isPasswordVisible)}
              className="absolute inset-y-0 right-4 flex items-center text-gray-500"
            >
              {isPasswordVisible ? <FiEyeOff size={20} /> : <FiEye size={20} />}
            </button>
          </div>

          {/* Confirm New Password Input */}
          <div className="relative mb-4">
            <input
              type={isConfirmPasswordVisible ? "text" : "password"}
              placeholder="Confirm New Password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              className="w-full p-3 bg-gray-800 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 pr-12"
            />
            <button
              type="button"
              onClick={() => setIsConfirmPasswordVisible(!isConfirmPasswordVisible)}
              className="absolute inset-y-0 right-4 flex items-center text-gray-500"
            >
              {isConfirmPasswordVisible ? <FiEyeOff size={20} /> : <FiEye size={20} />}
            </button>
          </div>

          {/* Submit Button */}
          <button
            className="w-full py-3 px-4 bg-gradient-to-r from-green-500 to-emerald-600 text-white font-bold rounded-lg shadow-lg hover:from-green-600 hover:to-emerald-700 focus:outline-none focus:ring-2 focus:ring-green-500 transition duration-200"
            type="submit"
          >
            Set New Password
          </button>
        </form>
      </div>
    </div>
  );
};

export default ChangePassword;
