import React from "react";
import { FaTelegramPlane } from "react-icons/fa";
import {
  FaPhoneVolume,
  FaLocationDot,
  FaWhatsapp,
  FaDiscord,
} from "react-icons/fa6";
import { MdEmail } from "react-icons/md";
import { useForm } from "react-hook-form";

const ContactUs = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();
  const onSubmit = (data) => {
    console.log("Form Data:", data);
    alert("Message sent successfully!");
    reset();
  };
  return (
    <div className="grid place-items-center bg-[#1E293B] xl:h-[85vh] min-h-fit ">
      {/* heading */}
      <div className="pt-10">
        <h2 className="font-poppins font-bold text-4xl text-[#21B573] text-center my-4">
          Contact Us
        </h2>
        <p className="font-poppins font-medium text-xl text-center text-[#E3E3E3] mb-8">
          Any question or remarks? Just write us a message!
        </p>
      </div>

      {/* Form */}
      <div className="bg-[#475569] w-11/12 h-[500px] p-1 flex flex-row mb-8 border border-gray-200 rounded-xl ">
       
        {/* Left side */}
        <div className="bg-white flex flex-col justify-between items-right py-5 rounded-xl">
          {/* title  */}
          <div className="pl-5">
            <h1 className="font-semibold text-2xl">Contact Information</h1>
            <p className="text-sm font-medium">
              Say something to start a live chat!
            </p>
          </div>
          {/* contact section */}
          <div className="flex flex-col gap-8 pl-5">
            <a
              href="tel:+91-123-456-789"
              className="flex items-center gap-2 text-sm"
            >
              <FaPhoneVolume className="mr-2 text-xl" />
              +91-123-456-789
            </a>
            <a
              href="mailto:ABC@gmail.com"
              className="flex items-center gap-2 text-sm"
            >
              <MdEmail className="mr-2 text-2xl" />
              ABC@gmail.com
            </a>
            <span className="flex items-center gap-2 text-sm pr-4">
              <FaLocationDot className="mr-2 text-3xl" />
              132 Dartmouth Street Boston, Massachusetts 02156 United States
            </span>
          </div>
          {/* social media */}
          <div className="flex gap-8 mt-4 pb-8 flex-row justify-center">
            <span className="flex items-center justify-center rounded-full border w-10 h-10 bg-[#21B573] ">
              <FaWhatsapp className="w-[28px] h-[28px] text-white " />
            </span>
            <span className="flex items-center justify-center rounded-full border w-10 h-10 bg-[#21B573] ">
              <FaDiscord className="w-[28px] h-[28px] text-white " />
            </span>
            <span className="flex items-center justify-center rounded-full border w-10 h-10 bg-[#21B573] ">
              <FaTelegramPlane className="w-[28px] h-[28px] text-white " />
            </span>
          </div>
        </div>

        {/* Right side */}
        <div className=" w-full ml-10 mt-10 mb-12 mr-8">
          <form
            onSubmit={handleSubmit(onSubmit)}
            action=""
            className="flex flex-col text-white text-sm font-light gap-4"
          >
            <div className="grid grid-cols-2 gap-8">
              {/* First Name input */}
              <div>
                <label className="block mb-2 font-medium">First Name</label>
                <input
                  type="text"
                  {...register("firstname", {
                    required: "First name is required",
                  })}
                  className="w-full bg-transparent border-b border-gray-500 text-white text-sm focus:outline-none focus:border-white"
                  placeholder="John"
                />
                {errors.firstname && (
                  <p className="text-red-500 text-sm">
                    {errors.firstname.message}
                  </p>
                )}
              </div>
              {/* Last Name input */}
              <div>
                <label className="block mb-2 font-medium">Last Name</label>
                <input
                  type="text"
                  {...register("lastname", {
                    required: " Last name is required",
                  })}
                  className="w-full bg-transparent border-b border-gray-500 text-white text-sm focus:outline-none focus:border-white"
                  placeholder="Doe"
                />
                {errors.lastname && (
                  <p className="text-red-500 text-sm">
                    {errors.lastname.message}
                  </p>
                )}
              </div>
              {/* Email input */}
              <div>
                <label className="block mb-2 font-medium">Email</label>
                <input
                  type="email"
                  {...register("email", {
                    required: "Email is required",
                    pattern: {
                      value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                      message: "Enter a valid email",
                    },
                  })}
                  className="w-full bg-transparent border-b border-gray-500 text-white text-sm focus:outline-none focus:border-white"
                  placeholder="H8C7o@example.com"
                />
                {errors.email && (
                  <p className="text-red-500 text-sm">{errors.email.message}</p>
                )}
              </div>
              {/* phone input */}
              <div>
                <label className="block mb-2 font-medium">Phone Number</label>
                <input
                  type="tel"
                  {...register("phone", {
                    required: "Phone number is required",
                    pattern: {
                      value: /^[0-9]{10}$/,
                      message: "Enter a valid 10-digit phone number",
                    },
                  })}
                  className="w-full bg-transparent border-b border-gray-500 text-white text-sm focus:outline-none focus:border-white"
                  placeholder="+91-123-456-789"
                />
                {errors.phone && (
                  <p className="text-red-500 text-sm">{errors.phone.message}</p>
                )}
              </div>
            </div>
            {/* Message input */}
            <div className="mt-4 ">
              <label className="block mb-2 font-medium">Message</label>
              <textarea
                {...register("message", { required: "Message is required" })}
                rows="1"
                className="w-full bg-transparent border-b border-gray-500 text-white text-sm focus:outline-none focus:border-white"
                placeholder="Write your message."
              ></textarea>
              {errors.message && (
                <p className="text-red-500 text-sm">{errors.message.message}</p>
              )}
            </div>

            {/* Submit button */}
            <div className="flex justify-end mt-4">
              <button
                type="submit"
                className="w-1/5 bg-[#1E293B] text-white py-3 rounded "
              >
                Send Message
              </button>{" "}
            </div>
            <div className=" absolute right-36 -bottom-24">
              <img
                src="flying-arrow.png"
                alt=""
                className="w-[200px] xl:w-[250px]"
              />
            </div>
          </form>
        </div>
      </div>

    </div>
  );
};

export default ContactUs;