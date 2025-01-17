describe('Session Creation spec', () => {
    beforeEach(() => {
        //cy.visit('/sessions/create');
        cy.fixture('sessions').then((sessions) => {
            cy.intercept({method: 'GET', url: '/api/session'}, sessions).as('sessions')
        })
        
        cy.fixture('teachers').then((trachers) => {
            cy.intercept({method: 'GET', url: '/api/teacher'}, trachers).as('teachers');
        })
        

        cy.adminLogin();

        cy.contains('span', 'Create').click();

    });

    it ('Session Creation successfull', () => {
        
        cy.get('input[formControlName=name]').type('Session Name');
        cy.get('textarea[formControlName=description]').type('Session Description');
        cy.get('input[formControlName=date]').type('2024-12-12');
        cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Hélène THIERCELIN').click();
        
        cy.fixture('createSession').then((createSession) => {
            cy.intercept({method: 'POST', url: '/api/session',}, createSession).as('createSession')
        });

        cy.get('button[type=submit]').click();
        
        cy.url().should('include', '/sessions');
        cy.get('.mat-simple-snack-bar-content').contains("Session created !")

    });

    it ('Session Creation failed', () => {
        cy.get('input[formControlName=name]').type('Session Name');
        cy.get('textarea[formControlName=description]').type('Session Description');
        cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Hélène THIERCELIN').click();
        
        cy.fixture('createSession').then((createSession) => {
            cy.intercept({method: 'POST', url: '/api/session',}, {
                statusCode: 400,
                body: createSession
            }).as('createSession')
        });

        cy.get(':button').should('be.disabled')
        
    });
});