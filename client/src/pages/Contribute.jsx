import React from "react";
import Footer from "../components/Footer";

const Contribute = () => {
  return (
    <div>
      <div className="bg-darkbg min-h-[80vh]  flex   items-center ">
        <div className="flex justify-center items-center w-full h-full gap-11   ">
          {/* right side */}
          <div className="w-[500px]   ">
            <img src="/public/contribute_1.png" alt="" className=" w-full" />
          </div>
          {/* left side */}
          <div className="flex flex-col gap-5 w-[745px]">
            <p className="capitalize text-white  text-4xl font-bold text-center leading-normal">
              <span className="text-btngreen ">Contribute</span> your resource
              for <br />
              fun & friends
            </p>
            <div className="flex flex-row gap-[100px]  ">
              <button className="bg-btngreen w-[300px] h-[64px] rounded-xl text-whitee ">
                <span className="text-2xl font-bold">Notes</span>
              </button>
              <button className="bg-midblue w-[300px]  h-[64px] rounded-xl text-whitee">
                <span className="text-2xl font-bold">Project</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Contribute;
