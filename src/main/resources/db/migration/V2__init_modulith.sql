BEGIN;

-- Tabla estándar requerida por Spring Modulith para el patrón Outbox
CREATE TABLE IF NOT EXISTS event_publication
(
    id               UUID NOT NULL,
    event_type       VARCHAR(512) NOT NULL,
    listener_id      VARCHAR(512) NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date  TIMESTAMP WITH TIME ZONE,
    serialized_event VARCHAR(4000) NOT NULL,
    completion_attempts INTEGER NOT NULL DEFAULT 0,
    last_resubmission_date  TIMESTAMP WITH TIME ZONE,
    status                  VARCHAR(20) NOT NULL DEFAULT 'PUBLISHED',
    PRIMARY KEY (id)
);

CREATE INDEX idx_event_publication_status ON event_publication(status);

END;