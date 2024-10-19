import { Box, Typography, Grid2, CardMedia } from '@mui/material';
import { FC } from 'react';
import styles from '../sass/Header.module.scss'

interface HeaderProps {
  publishingDate: string;
  logoUrl: string;
  language: string;
  onLanguageChange: (lang: string) => void;
}

const Header: FC<HeaderProps> = ({ publishingDate, logoUrl, language, onLanguageChange}) => {
  return (
    <Box className={styles.headerContainer}>
      <Grid2 container alignItems="center">
        <Grid2 size={4} textAlign='left'>
          <Typography variant="body2" className={styles.date}>{publishingDate}</Typography>
        </Grid2>
        <Grid2 size={4}>
          <CardMedia
            component="img"
            height="40"
            image={logoUrl}
            alt="The New York Times"
            sx={{ objectFit: 'contain' }}
          />
        </Grid2>
        <Grid2 size={4} textAlign='right'>
          <div className={styles.languageContainer}>
            <Typography
              variant="body2"
              className={`${styles.languageOption} ${language === 'en' ? styles.active : ''}`}
              onClick={() => onLanguageChange('en')}
              marginRight='4px'
            >
              ENG
            </Typography>
            <Typography variant="body2" className={styles.languageDivider}>
              \
            </Typography>
            <Typography
              variant="body2"
              className={`${styles.languageOption} ${language === 'es' ? styles.active : ''}`}
              onClick={() => onLanguageChange('es')}
              marginLeft='4px'
            >
              ESP
            </Typography>
          </div>
        </Grid2>
      </Grid2>
    </Box>
  );
};

export default Header;