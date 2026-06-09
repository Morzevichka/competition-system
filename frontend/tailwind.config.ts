// tailwind.config.ts
import type { Config } from 'tailwindcss'

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

        'primary': '#dbfcff',
        'primary-container': '#00f0ff',
        'on-primary': '#00363a',
        'on-primary-container': '#006970',

        'secondary': '#d1bcff',
        'secondary-container': '#7000ff',
        'on-secondary': '#3c0090',
        'on-secondary-container': '#ddcdff',

        'tertiary': '#f5f5f5',
        'tertiary-container': '#d9d9d9',
        'on-tertiary': '#2f3131',
        'on-tertiary-container': '#5d5f5f',

        'error': '#ffb4ab',
        'error-container': '#93000a',
        'on-error': '#690005',
        'on-error-container': '#ffdad6',

        'surface-tint': '#00dbe9',
        'inverse-surface': '#e3e0f3',
        'inverse-on-surface': '#2f2f3d',
        'inverse-primary': '#006970',

        'primary-fixed': '#7df4ff',
        'primary-fixed-dim': '#00dbe9',
        'on-primary-fixed': '#002022',
        'on-primary-fixed-variant': '#004f54',

        'secondary-fixed': '#e9ddff',
        'secondary-fixed-dim': '#d1bcff',
        'on-secondary-fixed': '#23005b',
        'on-secondary-fixed-variant': '#5700c9',

        'tertiary-fixed': '#e2e2e2',
        'tertiary-fixed-dim': '#c6c6c7',
        'on-tertiary-fixed': '#1a1c1c',
        'on-tertiary-fixed-variant': '#454747',
      },

      fontFamily: {
        display: ['Anybody', 'system-ui', 'sans-serif'],
        headline: ['Anybody', 'system-ui', 'sans-serif'],
        body: ['"Hanken Grotesk"', 'system-ui', 'sans-serif'],
        mono: ['"JetBrains Mono"', 'monospace'],
      },

      fontSize: {
        'display-lg': ['64px', { lineHeight: '1.1', letterSpacing: '-0.02em' }],
        'headline-lg': ['32px', { lineHeight: '1.2' }],
        'headline-lg-mobile': ['24px', { lineHeight: '1.2' }],
        'body-md': ['16px', { lineHeight: '1.6' }],
        'data-label': ['12px', { lineHeight: '1', letterSpacing: '0.1em' }],
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
        16: '64px',
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