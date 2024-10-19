import { Container, Box, CircularProgress } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import Header from "../components/Header";
import FeedList from "../components/FeedList";
import styles from "../sass/FeedPage.module.scss"

interface FeedItem {
    title: string;
    date: string;
    description: string;
    author: string;
    contentUrl: string;
    imageUrl: string;
}

interface Feed {
    logoUrl: string;
    publishingDate: string;
    feedItems: FeedItem[];
}

const FeedPage = () => {

    const [feed, setFeed] = useState<Feed | null>(null);
    const [language, setLanguage] = useState<string>('en');
    const [loading, setLoading] = useState<boolean>(false);

    const fetchFeed = (lang: string) => {
        setLoading(true);
        axios.get<Feed>('http://localhost:8080/api/feed',{ params: { lang } })
        .then(response => setFeed(response.data))
        .catch(error => console.error('Error fetching feed:', error))
        .finally(() => { setLoading(false) });
    };

    useEffect(() => {
        fetchFeed(language)
    }, [language]);

    return (
        <Box className={styles.feedPageContainer}>
            <Header publishingDate={feed?.publishingDate || ''} logoUrl={feed?.logoUrl || ''} language={language} onLanguageChange={setLanguage} />
            { loading ? (
                <Box className={styles.spinnerBox}>
                    <CircularProgress sx={{ color: '#000' }} />
                </Box>
            ) : (
                <Container maxWidth="md" className={styles.feedContent} sx={{ boxShadow: 3 }}>
                    { feed && <FeedList feedItems={feed.feedItems} /> }
                </Container>
            )}
        </Box>
    );
}

export default FeedPage;