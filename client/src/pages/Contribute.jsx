import React from "react";
import Footer from "../components/Footer";
import { Link } from 'react-router-dom';

const Contribute = () => {
  return (
    <div>
      <div className="bg-darkbg min-h-[80vh]  flex   items-center ">
        <div className="flex justify-center items-center w-full h-full gap-11   ">
          {/* right side */}
          <div className="w-[500px]   ">
            <img src="/contribute_1.png" alt="" className=" w-full" />
          </div>
          {/* left side */}
          <div className="flex flex-col gap-5 w-[745px]">
            <p className="capitalize text-white  text-4xl font-bold text-center leading-normal">
              <span className="text-btngreen ">Upload</span> your resources
              to <br />
              friends & fun
            </p>
            <div className="grid grid-cols-2 gap-x-[100px] gap-y-[50px] pt-10">
              <Link to="/contribute/notesUpload">
                <button className="bg-btngreen w-[300px] h-[64px] rounded-xl custom-shadow text-whitee text-2xl font-bold hover:scale-105">
                  Notes
                </button>
              </Link>
              
              <Link to="/contribute/projectUpload">
                <button className="bg-midblue w-[300px] h-[64px] rounded-xl custom-shadow text-whitee text-2xl font-bold hover:scale-105">
                  Projects
                </button>
              </Link>

              <Link to="/contribute/organizerUpload">
                <button className="bg-yellow-600 w-[300px] h-[64px] rounded-xl custom-shadow text-whitee text-2xl font-bold hover:scale-105">
                  organizer
                </button>
              </Link>
              
              <Link to="/contribute/pyqUpload">
                <button className="bg-red-500 w-[300px] h-[64px] rounded-xl custom-shadow text-whitee text-2xl font-bold hover:scale-105">
                  PYQ
                </button>
              </Link>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Contribute;
