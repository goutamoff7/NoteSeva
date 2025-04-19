import React from "react";
import { FaCheckCircle } from "react-icons/fa";
const VerifiedBadge = ({ isVerified }) => {
  return (
    isVerified && (
      <div className="flex flex-row items-center gap-1 ">
        <FaCheckCircle className="text-green-500 " />
        <span className="text-iconColor text-sm font-normal rounded-full">
          Verified
        </span>
      </div>
    )
  );
};

export default VerifiedBadge;
