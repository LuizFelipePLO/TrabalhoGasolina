package org.example;

public class CentroDistribuicao {
    public enum SITUACAO {
        NORMAL, SOBRAVISO, EMERGENCIA
    }

    public enum TIPOPOSTO {
        COMUM, ESTRATEGICO
    }

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    // Variavéis da minha cabeça.
    int gasolina;
    int aditivo;
    int alcool1;
    int alcool2;
    SITUACAO situacao;

    /**
     * O método construtor recebe as quantidades iniciais de gasolina nos tanques e
     * ajusta a “situação” de acordo.
     * Caso algum dos parâmetros tenha valor inválido o método deve gerar uma
     * ILLEGAL_ARGUMENT_EXCEPTION
     * (isso vale também para quantidades iniciais de álcool que devem ser iguais).
     */
    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) throws IllegalArgumentException {
        if (tAlcool1 != tAlcool2) {
            throw new IllegalArgumentException("Quantidade de Álcool diferente!");
        }

        if (tAditivo < 0 || tGasolina < 0 || tAlcool1 < 0) {
            throw new IllegalArgumentException("Argumentos inválidos!");
        }

        if (tAditivo <= MAX_ADITIVO) {
            aditivo = tAditivo;
        }

        if (tGasolina <= MAX_GASOLINA) {
            gasolina = tGasolina;
        }

        // Capacidade do primeiro Tanque é 1250L
        if (tAlcool1 <= (MAX_ALCOOL / 2)) {
            alcool1 = tAlcool1;
        }

        // Capacidade do segundo Tanque é 1250L
        if (tAlcool2 <= (MAX_ALCOOL / 2)) {
            alcool2 = tAlcool2;
        }

        defineSituacao();
    }

    /**
     * O método “defineSituacao” ajusta a situação de acordo com as regras.
     * Ele deve ser chamado tanto pelos métodos que sinalizam a chegada de
     * componentes no centro de distribuição
     * quanto pelo método “encomendaCombustivel” que sinaliza o fornecimento de
     * combustível para um posto.
     * <p>
     * Quando todos os tanques estiverem acima de 50% da capacidade o sistema opera
     * em modo NORMAL e as
     * encomendas são entregues normalmente para qualquer tipo de posto.
     * <p>
     * Se o volume armazenado em QUALQUER UM dos tanques cair abaixo de 50% o
     * sistema passa a operar em modo SOBRAVISO.
     * Neste modo o sistema só entrega 50% do que é solicitado pelos postos COMUNS e
     * o total solicitado pelos ESTRATEGICOS.
     * <p>
     * Caso o volume em QUALQUER UM dos tanques caia abaixo de 25%, então o sistema
     * passa a operar em modo de EMERGËNCIA e
     * as encomendas dos postos COMUNS deixam de ser atendidas e as dos ESTRATÉGICOS
     * são atendidas em 50%.
     */
    public void defineSituacao() {

        // SITUAÇÃO NORMAL
        // 50% de Aditivo
        if (gettAditivo() >= 250) {

            // 50% de Alcool
            if (gettAlcool1() >= 625) {
                if (gettAlcool2() >= 625) {

                    // 50% de Gasolina
                    if (gettGasolina() >= 5000) {
                        situacao = SITUACAO.NORMAL;
                    }
                }
            }
        }

        // SITUAÇÃO SOBRAVISO
        // Menos de 50% de Aditivo e acima de 25%
        if (gettAditivo() < 250 && gettAditivo() >= 125) {
            situacao = SITUACAO.SOBRAVISO;
        }

        // Menos de 50% de Alcool e acima de 25%
        if (gettAlcool1() < 625 && gettAlcool1() >= 312) {
            situacao = SITUACAO.SOBRAVISO;
        }

        if (gettAlcool2() < 625 && gettAlcool2() >= 312) {
            situacao = SITUACAO.SOBRAVISO;
        }

        // Menos de 50% de Gasolina e acima de 25%
        if (gettGasolina() < 5000 && gettGasolina() >= 2500) {
            situacao = SITUACAO.SOBRAVISO;
        }

        // SITUAÇÃO EMERGENCIA
        // Menos de 25% de Aditivo
        if (gettAditivo() < 125) {
            situacao = SITUACAO.EMERGENCIA;
        }

        // Menos de 25% de Alcool
        if (gettAlcool1() < 312) {
            situacao = SITUACAO.EMERGENCIA;
        }
        if (gettAlcool2() < 312) {
            situacao = SITUACAO.EMERGENCIA;
        }

        // Menos de 25% de Gasolina
        if (gettGasolina() < 2500) {
            situacao = SITUACAO.EMERGENCIA;
        }
    }

    public SITUACAO getSituacao() {
        return situacao;
    }

    public int gettGasolina() {
        return gasolina;
    }

    public int gettAditivo() {
        return aditivo;
    }

    public int gettAlcool1() {
        return alcool1;
    }

    public int gettAlcool2() {
        return alcool2;
    }

    /**
     * Os métodos “recebeAditivo”, “recebeGasolina” e “recebeAlcool” são usados
     * quando o centro de distribuição
     * recebe carga dos componentes. Todos recebem por parâmetro a quantidade do
     * componente (aditivo, gasolina ou álcool)
     * recebida e retornam à quantidade que PUDERAM armazenar devido a limitação do
     * tamanho dos tanques e de quanto
     * ainda tinham armazenado.
     * <p>
     * Devem retornar “-1” caso a quantidade recebida por parâmetro seja inválida.
     * <p>
     * FALTA FAZER A VERIFICAÇÃO PRA DOUBLE
     */
    public int recebeAditivo(int qtdade) {

        int result;

        if (qtdade <= 0) {
            result = -1;
        } else {
            aditivo = aditivo + qtdade;

            // Verificação
            if (gettAditivo() > MAX_ADITIVO) {

                int resto = gettAditivo() - MAX_ADITIVO;
                aditivo = aditivo - resto;

                result = qtdade - resto;
            } else {
                result = qtdade;
            }
        }

        defineSituacao();
        return result;
    }

    public int recebeGasolina(int qtdade) {

        int result;

        if (qtdade <= 0) {
            result = -1;
        } else {
            gasolina = gasolina + qtdade;

            // Verificação
            if (gettGasolina() > MAX_GASOLINA) {

                int resto = gettGasolina() - MAX_GASOLINA;
                gasolina = gasolina - resto;

                result = qtdade - resto;
            } else {
                result = qtdade;
            }
        }

        defineSituacao();
        return result;
    }

    /**
     * Os tanques de álcool devem ter sempre a mesma quantidade de combustível de
     * maneira a manter o equilíbrio
     * da estrutura devido a forma como foram construídos. Isso vale tanto para o
     * armazenamento como para a retirada.
     */
    public int recebeAlcool(int qtdade) {

        int result;

        if (qtdade <= 0) {
            result = -1;
        } else {
            int divisaoQtd = qtdade / 2;

            alcool1 = alcool1 + divisaoQtd;
            alcool2 = alcool2 + divisaoQtd;

            // Verificação
            if (gettAlcool1() > (MAX_ALCOOL / 2) && gettAlcool2() > (MAX_ALCOOL / 2)) {
                int resto1 = gettAlcool1() - (MAX_ALCOOL / 2);
                int resto2 = gettAlcool2() - (MAX_ALCOOL / 2);

                alcool1 = alcool1 - resto1;
                alcool2 = alcool2 - resto2;

                result = qtdade - (resto1 + resto2);
            } else {
                result = qtdade;
            }
        }

        defineSituacao();
        return result;
    }

    /**
     * O método “encomendaCombustivel” é usado quando o centro de distribuição
     * recebe o pedido de um posto.
     * Este método recebe por parâmetro a quantidade solicitada pelo posto e o tipo
     * do posto.
     * Se o pedido puder ser atendido, o método retorna um arranjo com a quantidade
     * de combustível remanescente
     * em cada tanque, DEPOIS do pedido atendido.
     * <p>
     * As quantidades devem ser retornadas pela ordem: aditivo, gasolina, álcool T1
     * e álcool T2.
     * A primeira posição do arranjo é usada também para indicar códigos de erro.
     * No caso de ser recebido um valor inválido por parâmetro deve-se retornar “-7”
     * na primeira posição do arranjo,
     * se o pedido não puder ser atendido em função da “situação” retorna-se “-14”
     * e,
     * caso não haja combustível suficiente para completar a mistura, retorna-se
     * “-21”.
     * <p>
     * Na hora de fazer os cálculos multiplique os valores por 100.
     * Depois de feitos os cálculos dividam por 100 novamente e despreze a parte
     * fracionária;
     * <p>
     * Se houver falta de qualquer um dos componentes na quantidade adequada a
     * encomenda não pode ser entregue.
     * <p>
     * Quando todos os tanques estiverem acima de 50% da capacidade o sistema opera
     * em modo NORMAL e as
     * encomendas são entregues normalmente para qualquer tipo de posto.
     * <p>
     * Se o volume armazenado em QUALQUER UM dos tanques cair abaixo de 50% o
     * sistema passa a operar em modo SOBRAVISO.
     * Neste modo o sistema só entrega 50% do que é solicitado pelos postos COMUNS e
     * o total solicitado pelos ESTRATEGICOS.
     * <p>
     * Caso o volume em QUALQUER UM dos tanques caia abaixo de 25%, então o sistema
     * passa a operar em modo de EMERGËNCIA e
     * as encomendas dos postos COMUNS deixam de ser atendidas e as dos ESTRATÉGICOS
     * são atendidas em 50%.
     * <p>
     * a gasolina vendida nos postos é resultado de uma mistura de 3 componentes:
     * 5% de aditivo, 25% de álcool e 70% de gasolina pura.
     */
    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) {

        SITUACAO atual = getSituacao();
        int[] valores = new int[4];

        if (qtdade <= 0) {
            valores[0] = -7;
            return valores;
        }

        double targetAditivo = qtdade * 0.05;
        double targetAlcool = qtdade * 0.25;
        double targetGasolina = qtdade * 0.7;

        // Separação Extra pros tanque de Alcool
        double a1 = Math.floor(targetAlcool / 2);
        double a2 = Math.floor(targetAlcool / 2);

        // Verificação da quantidade de Aditivo necessário
        if (gettAditivo() < targetAditivo) {
            valores[0] = -21;
            return valores;
        }

        // Verificação da quantidade de Álcool necessário
        if (gettAlcool1() < a1 || gettAlcool2() < a2) {
            valores[0] = -21;
            return valores;
        }

        // Verificação da quantidade de Gasolina necessário
        if (gettGasolina() < targetGasolina) {
            valores[0] = -21;
            return valores;
        }

        if (atual == SITUACAO.NORMAL || (atual == SITUACAO.SOBRAVISO && tipoPosto == TIPOPOSTO.ESTRATEGICO)) {
            aditivo = (int) (aditivo - targetAditivo);
            alcool1 = (int) (alcool1 - a1);
            alcool2 = (int) (alcool2 - a2);
            gasolina = (int) (gasolina - targetGasolina);

            defineSituacao();

            // Aditivo, gasolina, álcool T1 e álcool T2.
            valores[0] = gettAditivo();
            valores[1] = gettGasolina();
            valores[2] = gettAlcool1();
            valores[3] = gettAlcool2();
        }

        if ((atual == SITUACAO.SOBRAVISO && tipoPosto == TIPOPOSTO.COMUM)
                || (atual == SITUACAO.EMERGENCIA && tipoPosto == TIPOPOSTO.ESTRATEGICO)) {

            aditivo = (int) (aditivo - Math.floor(targetAditivo / 2));
            alcool1 = (int) (alcool1 - Math.floor(a1 / 2));
            alcool2 = (int) (alcool2 - Math.floor(a2 / 2));
            gasolina = (int) (gasolina - Math.floor(targetGasolina / 2));

            defineSituacao();

            valores[0] = gettAditivo();
            valores[1] = gettGasolina();
            valores[2] = gettAlcool1();
            valores[3] = gettAlcool2();
        }

        if (atual == SITUACAO.EMERGENCIA && tipoPosto == TIPOPOSTO.COMUM) {
            valores[0] = -14;
            return valores;
        }

        return valores;
    }
    // FIM
}