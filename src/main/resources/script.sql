CREATE SCHEMA currencyexchange;

CREATE TABLE Currencies (
                            ID INT AUTO_INCREMENT PRIMARY KEY,
                            Code VARCHAR(3) UNIQUE NOT NULL,
                            FullName VARCHAR(255) NOT NULL,
                            Sign VARCHAR(5) NOT NULL
);

CREATE TABLE ExchangeRates (
                               ID INT AUTO_INCREMENT PRIMARY KEY,
                               BaseCurrencyId INT NOT NULL,
                               TargetCurrencyId INT NOT NULL,
                               Rate DECIMAL(6, 6) NOT NULL,
                               FOREIGN KEY (BaseCurrencyId) REFERENCES Currencies(ID),
                               FOREIGN KEY (TargetCurrencyId) REFERENCES Currencies(ID),
                               UNIQUE (BaseCurrencyId, TargetCurrencyId)
);
