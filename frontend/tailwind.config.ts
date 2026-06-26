// tailwind.config.ts
import type { Config } from 'tailwindcss';

export default <Partial<Config>>{
  theme: {
    extend: {
      colors: {
        'background': '#12121f',
        'surface': '#12121f',
        'surface-bright': '#383846',
        'surface-container-lowest': '#0d0d19',
        'surface-container-low': '#1a1a27',
        'surface-container': '#1f1e2b',
        'surface-container-high': '#292936',
        'surface-container-highest': '#343341',
        
        'on-surface': '#e3e0f3',
        'on-surface-variant': '#b9cacb',

        'outline': '#849495',
        'outline-variant': '#3b494b',

        'error': '#ffb4ab',
        'error-container': '#93000a',
        'on-error': '#690005',
        'on-error-container': '#ffdad6',

        // disable opacity 0.4

        'primary': '#e5fdff',
        
        'primary-container': '#33eeff',
        'primary-hover-container': '#66f2ff',
        'primary-active-container': '#99f7ff',
        'on-primary': '#002F33',

        'secondary-container': '#002f33',
        'secondary-hover-container': '#005e66',
        'secondary-active-container': '#008c99',
        'on-secondary': '#ccfbff',

        'tertiary-hover-container': '#002f33',
        'tertiary-active-container': '#002f33',
        'on-tertiary-active-container': '#66f2ff',
        'on-tertiary': '#33eeff',

        "light-cyan": {
          "50": "#e5fdff",
          "100": "#ccfbff",
          "200": "#99f7ff",
          "300": "#66f2ff",
          "400": "#33eeff",
          "500": "#00eaff",
          "600": "#00bbcc",
          "700": "#008c99",
          "800": "#005e66",
          "900": "#002f33",
          "950": "#002124"
        }

        // 'primary': '#dbfcff',
        // 'primary-container': '#00f0ff',
        // 'on-primary': '#00363a',
        // 'on-primary-container': '#003033',

        // 'secondary': '#d1bcff',
        // 'secondary-container': '#7000ff',
        // 'on-secondary': '#3c0090',
        // 'on-secondary-container': '#ddcdff',

        // 'tertiary': '#f5f5f5',
        // 'tertiary-container': '#d9d9d9',
        // 'on-tertiary': '#2f3131',
        // 'on-tertiary-container': '#5d5f5f',
      },

      fontFamily: {
        display: ['Anybody', 'system-ui', 'sans-serif'],
        headline: ['Anybody', 'system-ui', 'sans-serif'],
        body: ['"Hanken Grotesk"', 'system-ui', 'sans-serif'],
        mono: ['"JetBrains Mono"', 'monospace'],
      },

      spacing: {
        1: '4px',
        2: '8px',
        3: '12px',
        4: '16px',
        5: '20px',
        6: '24px',
        8: '32px',
        12: '48px',
        16: '64px'
      },

      maxWidth: {
        container: '1440px',
      },

      boxShadow: {
        glow: '0 0 8px rgba(0, 240, 255, 0.6)',
        'glow-strong': '0 0 16px rgba(0, 240, 255, 0.8)',
      },

      borderRadius: {
        none: '0px',
      },

      borderWidth: {
        DEFAULT: '1px',
      },
    },
  },

  plugins: [],
}