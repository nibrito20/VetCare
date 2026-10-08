USE vetcare;

-- consulta 1 de filtro
SELECT Codigo, Nome, Especie, Raca FROM Animal WHERE Especie = 'Canino' ORDER BY Nome ASC;

-- consulta 2 com join
SELECT a.Codigo AS ID, an.Nome AS Animal, d.Nome AS Dono, f.Nome AS Veterinario, a.Custo
FROM Atendimento a
         JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo
         JOIN Dono d ON an.fk_Dono_CPF = d.CPF
         JOIN Funcionario f ON a.fk_Funcionario_CPF = f.CPF;

-- consulta 3 grafico
SELECT an.Especie, COUNT(a.Codigo) AS Total_Atendimentos, SUM(a.Custo) AS Total_Faturado
FROM Atendimento a
         JOIN Animal an ON a.fk_Animal_Codigo = an.Codigo
GROUP BY an.Especie;

-- 4 subconsulta com having
SELECT f.Nome AS Veterinario, COUNT(a.Codigo) AS Qtd, SUM(a.Custo) AS Total
FROM Funcionario f
         JOIN Atendimento a ON f.CPF = a.fk_Funcionario_CPF
GROUP BY f.CPF, f.Nome
HAVING SUM(a.Custo) > (SELECT AVG(Custo) FROM Atendimento);