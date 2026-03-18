# 🔐 Spring Security OAuth2 Authorization Server Case Study

[![Java CI with Maven](https://github.com/anishk835/target-case-study/actions/workflows/maven.yml/badge.svg)](https://github.com/anishk835/target-case-study/actions/workflows/maven.yml)
[![CodeQL](https://github.com/anishk835/target-case-study/actions/workflows/codeql.yml/badge.svg)](https://github.com/anishk835/target-case-study/actions/workflows/codeql.yml)

A complete demonstration of **Spring Security OAuth2 Authorization Server** with resource server and client applications. This multi-module Maven project showcases the implementation of OAuth2 authorization code flow, JWT token generation, and secure resource access patterns.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Testing the Flow](#testing-the-flow)
- [Endpoints](#endpoints)
- [Technology Stack](#technology-stack)

---

## 🎯 Overview

This project demonstrates a complete OAuth2/OpenID Connect implementation using Spring Security's authorization server. It includes:

- ✅ **OAuth2 Authorization Server** - Issues JWT access tokens
- ✅ **Resource Server** - Protects API resources with JWT validation
- ✅ **Client Application** - Consumes protected resources via OAuth2
- ✅ **User Management** - H2 database with JPA entities
- ✅ **Multiple Grant Types** - Authorization Code, Password, Refresh Token
- ✅ **RSA JWT Signing** - Secure token generation with key pairs

---

## 🏗 Architecture

```
┌─────────────┐         ┌─────────────────────┐         ┌──────────────────┐
│   Client    │────────▶│ Authorization Server│◀────────│ Resource Server  │
│ (Port 8080) │         │    (Port 9000)      │         │   (Port 9001)    │
└─────────────┘         └─────────────────────┘         └──────────────────┘
      │                           │                              │
      │  1. Request authorization  │                              │
      │──────────────────────────▶│                              │
      │                           │                              │
      │  2. User login & consent   │                              │
      │◀──────────────────────────│                              │
      │                           │                              │
      │  3. Authorization code    │                              │
      │◀──────────────────────────│                              │
      │                           │                              │
      │  4. Exchange for token    │                              │
      │──────────────────────────▶│                              │
      │                           │                              │
      │  5. Access token (JWT)    │                              │
      │◀──────────────────────────│                              │
      │                                                          │
      │  6. Access resource with token                          │
      │─────────────────────────────────────────────────────────▶│
      │                                                          │
      │  7. Validate JWT & return resource                      │
      │◀─────────────────────────────────────────────────────────│
```

---

## 📦 Project Structure

```
target-case-study/
├── model/                          # Shared domain model
│   └── src/main/java/
│       └── com/casestudy/db/
│           ├── entity/
│           │   └── CaseStudyUser.java    # User entity with JPA annotations
│           └── repository/
│               └── UserRepository.java    # Spring Data JPA repository
│
├── auth-server/                    # OAuth2 Authorization Server
│   ├── src/main/java/
│   │   └── com/casestudy/security/auth/server/
│   │       ├── config/
│   │       │   ├── AuthServerConfig.java       # OAuth2 server configuration
│   │       │   └── SecurityConfig.java         # Security filter chain
│   │       └── service/
│   │           ├── CaseStudyAuthServerAuthenticationProvider.java
│   │           └── CaseStudyUserDetailsService.java
│   └── src/main/resources/
│       ├── application.yml         # Server runs on port 9000
│       └── data.sql               # Sample user data
│
├── resource-server/                # Protected API Resources
│   ├── src/main/java/
│   │   └── com/casestudy/security/resource/server/
│   │       ├── config/
│   │       │   └── SecurityConfig.java         # JWT validation config
│   │       └── controller/
│   │           └── ResouceController.java      # Protected endpoints
│   └── src/main/resources/
│       └── application.yml         # Server runs on port 9001
│
├── client/                         # OAuth2 Client Application
│   ├── src/main/java/
│   │   └── com/casestudy/client/
│   │       ├── config/
│   │       │   └── SecurityConfig.java         # OAuth2 client config
│   │       └── controller/
│   │           └── HelloController.java        # Client endpoints
│   └── src/main/resources/
│       └── application.yml         # Server runs on port 8080
│
└── pom.xml                         # Parent POM
```

---

## ✅ Prerequisites

- **Java**: 17 or higher
- **Maven**: 3.8+
- **Git**: For cloning the repository

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone git@github.com:anishk835/target-case-study.git
cd target-case-study
```

### 2. Configure Host File (Required)

The authorization server uses `auth-server` as the issuer hostname. Add this to your hosts file:

**macOS/Linux:**
```bash
sudo nano /etc/hosts
```

**Windows:**
```bash
notepad C:\Windows\System32\drivers\etc\hosts
```

Add the following line:
```
127.0.0.1    auth-server
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Start the Applications

Open **three separate terminal windows**:

**Terminal 1: Authorization Server**
```bash
cd auth-server
mvn spring-boot:run
```
✅ Runs on `http://auth-server:9000`

**Terminal 2: Resource Server**
```bash
cd resource-server
mvn spring-boot:run
```
✅ Runs on `http://localhost:9001`

**Terminal 3: Client Application**
```bash
cd client
mvn spring-boot:run
```
✅ Runs on `http://localhost:8080`

---

## ⚙️ Configuration

### Authorization Server

**OAuth2 Client Registration:**
- **Client ID**: `api-client`
- **Client Secret**: `secret`
- **Grant Types**: Authorization Code, Password, Refresh Token
- **Scopes**: `openid`, `api.read`
- **Redirect URIs**:
  - `http://127.0.0.1:8080/authorized`
  - `http://127.0.0.1:8080/login/oauth2/code/api-client-oicd`

**Database (H2 In-Memory):**
- **Console**: `http://auth-server:9000/h2-console`
- **JDBC URL**: `jdbc:h2:mem:db`
- **Username**: `sa`
- **Password**: `sa`

### Default Users

Users are loaded from `auth-server/src/main/resources/data.sql`:

| Username | Password | Role |
|----------|----------|------|
| user1    | password1 | USER |
| user2    | password2 | USER |

---

## 🧪 Testing the Flow

### Option 1: Authorization Code Flow (Browser-based)

1. **Initiate Authorization:**
   Open your browser and navigate to:
   ```
   http://localhost:8080/oauth2/authorization/api-client-authorization-code
   ```

2. **Login:**
   - Username: `user1`
   - Password: `password1`

3. **Grant Consent:**
   - Approve the requested scopes (`api.read`)

4. **Access Protected Resource:**
   ```
   http://localhost:8080/api/hello
   ```

### Option 2: Using cURL

**1. Get Authorization Code (Browser Required):**

Navigate to:
```
http://auth-server:9000/oauth2/authorize?response_type=code&client_id=api-client&scope=api.read&redirect_uri=http://127.0.0.1:8080/authorized
```

After login and consent, you'll be redirected to:
```
http://127.0.0.1:8080/authorized?code=AUTHORIZATION_CODE
```

**2. Exchange Code for Access Token:**

```bash
curl -X POST http://auth-server:9000/oauth2/token \
  -u api-client:secret \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code" \
  -d "code=AUTHORIZATION_CODE" \
  -d "redirect_uri=http://127.0.0.1:8080/authorized"
```

**3. Access Protected Resource:**

```bash
curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  http://localhost:9001/api/resources
```

---

## 🔗 Endpoints

### Authorization Server (Port 9000)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/oauth2/authorize` | GET | Authorization endpoint |
| `/oauth2/token` | POST | Token endpoint |
| `/oauth2/jwks` | GET | JSON Web Key Set |
| `/.well-known/oauth-authorization-server` | GET | OAuth2 metadata |
| `/h2-console` | GET | H2 database console |

### Resource Server (Port 9001)

| Endpoint | Method | Auth Required | Description |
|----------|--------|---------------|-------------|
| `/api/resources` | GET | ✅ JWT Token | Protected resource endpoint |

### Client Application (Port 8080)

| Endpoint | Method | Auth Required | Description |
|----------|--------|---------------|-------------|
| `/hello` | GET | ❌ Public | Public endpoint |
| `/api/hello` | GET | ✅ OAuth2 Login | Protected endpoint |
| `/authorized` | GET | - | OAuth2 redirect URI |

---

## 🛠 Technology Stack

### Core Framework
- **Spring Boot**: 2.7.4
- **Spring Security**: OAuth2 Authorization Server
- **Java**: 17

### Security & Authentication
- **Spring Security OAuth2 Authorization Server**: 0.3.1
- **Spring Security OAuth2 Resource Server**: JWT validation
- **Spring Security OAuth2 Client**: OAuth2 login

### Data & Persistence
- **Spring Data JPA**: Database abstraction
- **H2 Database**: In-memory database
- **Hibernate**: ORM

### Utilities
- **Lombok**: Reduce boilerplate code
- **Nimbus JOSE+JWT**: JWT and JWK handling

---

## 📝 Key Features

### 1. Custom Authentication Provider
The project includes a custom `CaseStudyAuthServerAuthenticationProvider` that authenticates users against a JPA-backed user repository.

### 2. JWT Token Generation
Uses RSA-based JWT signing with dynamically generated key pairs (2048-bit).

### 3. Multi-Module Architecture
Clean separation of concerns with shared model module and independent server applications.

### 4. H2 Console Access
Debug and inspect user data via the H2 web console during development.

### 5. Authorization Consent
Users must explicitly approve requested scopes before token issuance.

---

## 🔧 Development

### Running Tests

```bash
mvn test
```

### Building Without Tests

```bash
mvn clean package -DskipTests
```

### Accessing H2 Console

1. Navigate to: `http://auth-server:9000/h2-console`
2. Use connection details:
   - **JDBC URL**: `jdbc:h2:mem:db`
   - **Username**: `sa`
   - **Password**: `sa`

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📄 License

This project is created for educational and case study purposes.

---

## 👤 Author

**Anish Kumar**
- GitHub: [@anishk835](https://github.com/anishk835)

---

## 🐛 Troubleshooting

### Issue: "Unable to resolve issuer"
**Solution**: Ensure `auth-server` is added to your `/etc/hosts` file pointing to `127.0.0.1`

### Issue: "Connection refused on port 9000"
**Solution**: Make sure the authorization server is running and listening on port 9000

### Issue: "Invalid redirect URI"
**Solution**: Verify the redirect URI in the client configuration matches the registered URI in `AuthServerConfig.java`

---

## 📚 Additional Resources

- [Spring Security OAuth2 Authorization Server](https://spring.io/projects/spring-authorization-server)
- [OAuth 2.0 RFC 6749](https://tools.ietf.org/html/rfc6749)
- [JWT RFC 7519](https://tools.ietf.org/html/rfc7519)

---

**⭐ If you find this project helpful, please give it a star!**
