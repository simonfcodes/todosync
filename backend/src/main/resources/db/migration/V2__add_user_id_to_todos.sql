ALTER TABLE todos ADD COLUMN user_id UUID REFERENCES users(id) ON DELETE CASCADE;
-- Backfill existing rows - but there should be no rows at this point.
ALTER TABLE todos ALTER COLUMN user_id SET NOT NULL;