package com.vanna.project_eaters.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

@Component
public class JsonSchemaValidationFilter extends OncePerRequestFilter {

    private final Schema schema;

    public JsonSchemaValidationFilter() throws IOException {
        // Загружаем JSON Schema из resources
        try (InputStream inputStream = getClass().getResourceAsStream("/schemas/signup-schema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            this.schema = SchemaLoader.load(rawSchema);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Только для POST запроса на /api/auth/signup
        if (request.getRequestURI().equals("/api/auth/signup") && request.getMethod().equalsIgnoreCase("POST")) {
            try {
                String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JSONObject jsonObject = new JSONObject(json);

                // Валидация JSON по схеме
                schema.validate(jsonObject);

            } catch (ValidationException e) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

