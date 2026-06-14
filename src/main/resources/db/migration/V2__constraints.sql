ALTER TABLE twitter.users_roles
    ADD CONSTRAINT fk_users_roles_user
        FOREIGN KEY (user_id) REFERENCES twitter.users(id) ON DELETE CASCADE;

ALTER TABLE twitter.users_roles
    ADD CONSTRAINT fk_users_roles_role
        FOREIGN KEY (role_id) REFERENCES twitter.roles(id) ON DELETE CASCADE;

ALTER TABLE twitter.tweet
    ADD CONSTRAINT fk_tweet_user
        FOREIGN KEY (user_id) REFERENCES twitter.users(id) ON DELETE CASCADE;

ALTER TABLE twitter.retweet
    ADD CONSTRAINT fk_retweet_user
        FOREIGN KEY (user_id) REFERENCES twitter.users(id) ON DELETE CASCADE;

ALTER TABLE twitter.retweet
    ADD CONSTRAINT fk_retweet_tweet
        FOREIGN KEY (tweet_id) REFERENCES twitter.tweet(id) ON DELETE CASCADE;

ALTER TABLE twitter.tweet_like
    ADD CONSTRAINT fk_like_user
        FOREIGN KEY (user_id) REFERENCES twitter.users(id) ON DELETE CASCADE;

ALTER TABLE twitter.tweet_like
    ADD CONSTRAINT fk_like_tweet
        FOREIGN KEY (tweet_id) REFERENCES twitter.tweet(id) ON DELETE CASCADE;

ALTER TABLE twitter.comment
    ADD CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id) REFERENCES twitter.users(id) ON DELETE CASCADE;

ALTER TABLE twitter.comment
    ADD CONSTRAINT fk_comment_tweet
        FOREIGN KEY (tweet_id) REFERENCES twitter.tweet(id) ON DELETE CASCADE;

CREATE INDEX idx_tweet_user_id ON twitter.tweet(user_id);
CREATE INDEX idx_comment_tweet_id ON twitter.comment(tweet_id);
CREATE INDEX idx_retweet_tweet_id ON twitter.retweet(tweet_id);
CREATE INDEX idx_like_tweet_id ON twitter.tweet_like(tweet_id);