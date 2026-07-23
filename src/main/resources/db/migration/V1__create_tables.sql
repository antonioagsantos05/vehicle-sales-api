CREATE TABLE vehicles (
 id UUID PRIMARY KEY, brand VARCHAR(80) NOT NULL, model VARCHAR(120) NOT NULL,
 manufacture_year INTEGER NOT NULL CHECK (manufacture_year >= 1886), color VARCHAR(50) NOT NULL,
 price NUMERIC(15,2) NOT NULL CHECK (price > 0), status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP NOT NULL, updated_at TIMESTAMP NOT NULL
);
CREATE TABLE sales (
 id UUID PRIMARY KEY, vehicle_id UUID NOT NULL UNIQUE REFERENCES vehicles(id), buyer_id VARCHAR(120) NOT NULL,
 sale_price NUMERIC(15,2) NOT NULL CHECK (sale_price > 0), purchased_at TIMESTAMP NOT NULL
);
CREATE INDEX idx_vehicles_status_price ON vehicles(status, price);
CREATE INDEX idx_sales_buyer_id ON sales(buyer_id);
