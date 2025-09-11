package com.banking_app.auth_service.infrastructure.config.filter_factory_method;

import org.springframework.web.server.WebFilter;

public interface FilterCreator {
  WebFilter create();
}
