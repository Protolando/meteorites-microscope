import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MeteoritesMicroscopeTestModule } from '../../../test.module';
import { RockDetailComponent } from 'app/entities/rock/rock-detail.component';
import { Rock } from 'app/shared/model/rock.model';

describe('Component Tests', () => {
  describe('Rock Management Detail Component', () => {
    let comp: RockDetailComponent;
    let fixture: ComponentFixture<RockDetailComponent>;
    const route = ({ data: of({ rock: new Rock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MeteoritesMicroscopeTestModule],
        declarations: [RockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
