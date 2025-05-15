import React from 'react'
import { FaWhatsapp, FaDiscord, FaTelegram } from "react-icons/fa";

const HCommunities = () => {
  return (
    <div className='fixed text-whitee text-[40px] flex flex-col space-y-[40px] right-4 mt-[10px] z-[100]'>
      <a
        href="https://whatsapp.com/channel/0029VaynHjm7IUYU5Sx5w63H"
        target="_blank"
        rel="noopener noreferrer"
      >
        <FaWhatsapp className='hover:text-[#25D366]' />
      </a>
      <a
        href="#"
        target="_blank"
        rel="noopener noreferrer"
      >
        <FaDiscord className='hover:text-[#7289da]' />
      </a>
      <a
        href="https://t.me/NoteSeva"
        target="_blank"
        rel="noopener noreferrer"
      >
        <FaTelegram className='hover:text-[#29A9EC]' />
      </a>
    </div>
  );
};

export default HCommunities;
