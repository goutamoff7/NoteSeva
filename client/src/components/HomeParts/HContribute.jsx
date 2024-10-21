import React from 'react';
import { MdOutlineKeyboardDoubleArrowRight } from "react-icons/md";


const Contribute = () => {
  return (
    <div className="my-[60px] flex items-center justify-center gap-[50px]">
        {/* Left Side: Illustration */}
        <div className="flex justify-center">
          <img
            src="contribute.png"
            alt="Person Contributing"
            className="w-[300px] h-[300px] object-contain"
          />
        </div>

        {/* Right Side: Text and Button */}
        <div className="bg-whitee rounded-3xl shadow-lg p-8 text-center max-w-md">
          <h2 className="text-2xl font-bold text-darkblue mb-4">
            Contribute towards us
          </h2>
          <p className="text-darkblack text-sm mb-6">
            Send us your Best Notes and Resources that will be helpful to all your Seniors, Juniors & Friends.
          </p>
            <button className="bg-btngreen text-white py-3 px-6 w-[200px] rounded-full shadow hover:bg-green-600 transition">
                Continue  <span className='text-xl font-extrabold'>→</span>
            </button>
        </div>
    </div>
  );
};

export default Contribute;
