import { Card, CardContent, CardMedia, Divider, Grid2, Typography } from "@mui/material";
import { FC, Fragment } from "react";
import styles from "../sass/FeedList.module.scss"

interface FeedItem {
    title: string;
    date: string;
    description: string;
    author: string;
    contentUrl: string;
    imageUrl: string;
}

interface FeedListProps {
    feedItems: FeedItem[];
}

const FeedList: FC<FeedListProps> = ({ feedItems }) => {
    return (
        <Grid2 container direction="column" alignItems='center'>
            {feedItems.map((feedItem, index) => (
                <Fragment key={index}>
                    <Card elevation={0} className={styles.card}>
                        <Grid2 container spacing={2} alignItems="flex-start">
                            <Grid2 size={8}>
                                <CardContent sx={{ pl: 0, pt: 0, pb: 2 }}>
                                    <Typography variant="overline" display="block" lineHeight="1.5">
                                        {feedItem.date}
                                    </Typography>
                                    <a href={feedItem.contentUrl} target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'none', color: 'inherit' }}>
                                        <Typography variant="h6" fontWeight='bold' gutterBottom>
                                            {feedItem.title}
                                        </Typography>
                                        <Typography variant="body2" gutterBottom>
                                            {feedItem.description}
                                        </Typography>
                                    </a>
                                    <Typography variant="caption" color="textSecondary" fontSize='0.6rem'>
                                        BY {feedItem.author}
                                    </Typography>
                                </CardContent>
                            </Grid2>
                            <Grid2 size={4}>
                                <a href={feedItem.contentUrl} target="_blank" rel="noopener noreferrer">
                                    <CardMedia
                                        component="img"
                                        height="160"
                                        image={feedItem.imageUrl}
                                        alt={feedItem.title}
                                        sx={{ mx: 'auto', display: 'block' }}
                                    />
                                </a>
                            </Grid2>
                        </Grid2>
                    </Card>
                    {index < feedItems.length - 1 && <Divider className={styles.divider} />}
                </Fragment>
            ))}
        </Grid2>
    );
};

export default FeedList;