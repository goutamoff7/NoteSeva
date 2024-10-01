// vite.config.js
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react'; // Ensure you have this line if using React

export default defineConfig({
  plugins: [react()],
});
