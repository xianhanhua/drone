-- 文件说明：SQLite 数据库初始化脚本，用来建表、建索引和插入演示数据。
-- SQLite 初始化脚本。
-- 后端第一次启动时会执行这个文件，用来创建无人机表并准备初始数据。

CREATE TABLE IF NOT EXISTS drones (
    -- 主键，自增 id
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    -- 无人机名称
    name VARCHAR(100) NOT NULL,
    -- 型号
    model VARCHAR(100) NOT NULL,
    -- 厂商
    manufacturer VARCHAR(100) NOT NULL,
    -- 序列号
    serial_number VARCHAR(80) NOT NULL,
    -- 无人机类型
    drone_type VARCHAR(50) NOT NULL,
    -- 最大续航时间，单位分钟
    max_flight_time_minutes INTEGER NOT NULL,
    -- 最大速度，单位 km/h
    max_speed_kmh INTEGER NOT NULL,
    -- 最大飞行高度，单位米
    max_altitude_meters INTEGER NOT NULL,
    -- 最大载重，单位 kg
    payload_capacity_kg REAL NOT NULL,
    -- 电池容量，单位 mAh
    battery_capacity_mah INTEGER NOT NULL,
    -- 状态
    status VARCHAR(30) NOT NULL,
    -- AI 自动生成的说明文字
    ai_profile TEXT,
    -- 创建时间
    created_at TIMESTAMP NOT NULL,
    -- 更新时间
    updated_at TIMESTAMP NOT NULL
);

-- 序列号唯一索引，保证不能出现两架无人机使用同一个序列号。
CREATE UNIQUE INDEX IF NOT EXISTS uk_drones_serial_number ON drones(serial_number);
-- 名称索引，加快按名称查询速度。
CREATE INDEX IF NOT EXISTS idx_drones_name ON drones(name);
-- 状态索引，加快按状态筛选速度。
CREATE INDEX IF NOT EXISTS idx_drones_status ON drones(status);

-- 下面插入演示数据；WHERE NOT EXISTS 用来避免重复插入同一条样例。
INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    max_flight_time_minutes, max_speed_kmh, max_altitude_meters,
    payload_capacity_kg, battery_capacity_mah, status, ai_profile,
    created_at, updated_at
)
SELECT
    'DJI Matrice 350 RTK', 'Matrice 350 RTK', 'DJI', 'SAMPLE-DJI-M350-RTK', '巡检型',
    55, 83, 5000, 2.70, 5880, '在役',
    '样例数据：行业级多载荷平台，适合电力巡检、应急测绘和安防巡逻；公开规格显示最长飞行时间约55分钟，最大水平速度约23m/s。',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SAMPLE-DJI-M350-RTK');

INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    max_flight_time_minutes, max_speed_kmh, max_altitude_meters,
    payload_capacity_kg, battery_capacity_mah, status, ai_profile,
    created_at, updated_at
)
SELECT
    'DJI Mavic 3 Enterprise', 'Mavic 3E', 'DJI', 'SAMPLE-DJI-M3E', '测绘型',
    45, 76, 6000, 0.00, 5000, '在役',
    '样例数据：轻量化测绘与巡检机型，适合快速建图、园区巡检和影像采集；公开规格显示最长飞行时间约45分钟。',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SAMPLE-DJI-M3E');

INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    max_flight_time_minutes, max_speed_kmh, max_altitude_meters,
    payload_capacity_kg, battery_capacity_mah, status, ai_profile,
    created_at, updated_at
)
SELECT
    'Autel EVO Max 4T', 'EVO Max 4T', 'Autel Robotics', 'SAMPLE-AUTEL-EVO-MAX-4T', '安防型',
    42, 83, 7000, 0.00, 8070, '待检',
    '样例数据：多传感器行业无人机，适合消防、搜救、执法和夜间巡检；公开规格显示最长飞行时间约42分钟。',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SAMPLE-AUTEL-EVO-MAX-4T');

INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    max_flight_time_minutes, max_speed_kmh, max_altitude_meters,
    payload_capacity_kg, battery_capacity_mah, status, ai_profile,
    created_at, updated_at
)
SELECT
    'Parrot ANAFI USA', 'ANAFI USA', 'Parrot', 'SAMPLE-PARROT-ANAFI-USA', '安防型',
    32, 53, 5000, 0.00, 3400, '维修',
    '样例数据：便携式热成像/可见光无人机，适合应急响应、公共安全和近距离巡查；公开规格显示最长飞行时间约32分钟。',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SAMPLE-PARROT-ANAFI-USA');

INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    max_flight_time_minutes, max_speed_kmh, max_altitude_meters,
    payload_capacity_kg, battery_capacity_mah, status, ai_profile,
    created_at, updated_at
)
SELECT
    'Skydio X10', 'X10', 'Skydio', 'SAMPLE-SKYDIO-X10', '巡检型',
    40, 72, 4572, 0.34, 8419, '在役',
    '样例数据：自主避障行业无人机，适合公共安全、基础设施巡检和夜间作业；公开规格显示最长飞行时间约40分钟，最大水平速度约20m/s。',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SAMPLE-SKYDIO-X10');

UPDATE drones
SET drone_type = 'Inspection',
    status = 'Active',
    ai_profile = 'Sample data: enterprise payload platform for power inspection, mapping, emergency response and patrol. Public specs list about 55 minutes of max flight time and about 23 m/s max horizontal speed.'
WHERE serial_number = 'SAMPLE-DJI-M350-RTK';

UPDATE drones
SET drone_type = 'Mapping',
    status = 'Active',
    ai_profile = 'Sample data: compact mapping and inspection drone for fast surveying, site inspection and image collection. Public specs list about 45 minutes of max flight time.'
WHERE serial_number = 'SAMPLE-DJI-M3E';

UPDATE drones
SET drone_type = 'Security',
    status = 'Pending',
    ai_profile = 'Sample data: multi-sensor enterprise drone for firefighting, search and rescue, law enforcement and night inspection. Public specs list about 42 minutes of max flight time.'
WHERE serial_number = 'SAMPLE-AUTEL-EVO-MAX-4T';

UPDATE drones
SET drone_type = 'Security',
    status = 'Repair',
    ai_profile = 'Sample data: portable thermal and visual drone for emergency response, public safety and close-range inspection. Public specs list about 32 minutes of max flight time.'
WHERE serial_number = 'SAMPLE-PARROT-ANAFI-USA';

UPDATE drones
SET drone_type = 'Inspection',
    status = 'Active',
    ai_profile = 'Sample data: autonomous obstacle-avoidance enterprise drone for public safety, infrastructure inspection and night operations. Public specs list about 40 minutes of max flight time and about 20 m/s max horizontal speed.'
WHERE serial_number = 'SAMPLE-SKYDIO-X10';
