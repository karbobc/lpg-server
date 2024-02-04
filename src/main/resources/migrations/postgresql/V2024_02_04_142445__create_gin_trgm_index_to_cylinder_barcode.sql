CREATE EXTENSION pg_trgm;
CREATE INDEX t_cylinder_gin_trgm_idx_barcode ON t_cylinder USING gin(barcode gin_trgm_ops);